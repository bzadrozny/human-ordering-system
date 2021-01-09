import React, {Component, useState} from 'react'
import Toolbar from "../../board/board-toolbar";
import {Accordion, Alert, Button, Card, Col, Container, Row} from "react-bootstrap";
import UserContext from "../../../context/user-context";
import {OrganisationAPI, CommissionAPI} from "../../../api/hos-service-api";
import AuthService from "../../../services/authentication/auth-service";
import CommissionDataForm from "./commission-data-form";
import DetailsRow from "../../common/details-row";
import ShowHideToggle from "../../common/show-hide-toggle";
import {RiAddLine, RiDeleteBinLine, RiEditLine} from "react-icons/ri";
import OverlayTriggerIcon from "../../common/overlay-trigger-icon";
import CommissionRecordDeleteModal from "../modal/commission-record-delete-modal";
import CommissionRecordDataFormModal from "../modal/commission-record-data-form-modal";

class CommissionForm extends Component {
  static contextType = UserContext

  constructor(props) {
    super(props);
    this.state = {
      form: {
        id: null,
        principal: undefined,
        organisation: undefined,
        location: undefined,
        description: undefined,
        status: {id: 0},
        records: []
      },
      organisations: [],
      locations: [],
      processing: false,
      validations: []
    }
  }

  async componentDidMount() {
    const user = this.context || await AuthService.me()
    const organisations = user.authorities.some(auth => auth.id === 0) ?
        await OrganisationAPI.allOrganisations().then(resp => resp.data) :
        [user.organisation];
    const organisation = organisations[0]

    const locations = await OrganisationAPI.organisationById(organisation.id)
        .then(resp => resp.data)
        .then(organisation => organisation.locations)
    const location = locations[0]

    const {id} = this.props.match.params;
    if (id == null) {
      this.setState({
        form: {
          ...this.state.form,
          principal: user,
          organisation: organisation.id,
          location: location.id
        },
        organisations: organisations,
        locations: locations,
        user
      })
    } else {
      CommissionAPI.commissionsById(id)
          .then(resp => resp.data)
          .then(commission => this.setState({
            form: {
              id: commission.id,
              principal: commission.principal,
              organisation: commission.location.organisation,
              location: commission.location.id,
              description: commission.description,
              status: commission.status,
              records: commission.records.map(record => ({
                ...record,
                commission: commission.id,
              }))
            },
            organisations: organisations,
            locations: locations,
            user
          }))
    }
  }

  cancel = () => {
    this.setState({
      form: {
        id: null,
        principal: undefined,
        organisation: undefined,
        location: undefined,
        description: undefined,
        status: {id: 0},
        records: []
      },
      organisations: [],
      locations: [],
      processing: false,
      validations: []
    })
    this.props.history.goBack()
  }

  handleFormChangeOrganisation = async (event) => {
    if (this.state.organisations.length === 1) {
      return
    }

    let organisation = event.target.value
    const locations = await OrganisationAPI.organisationById(organisation)
        .then(resp => resp.data)
        .then(organisation => organisation.locations)

    this.setState({
      form: {
        ...this.state.form,
        organisation: organisation
      },
      locations: locations
    })
  }

  handleFormChangeLocation = (event) => {
    let location = event.target.value
    this.setState({
      form: {
        ...this.state.form,
        location: location
      }
    })
  }

  handleFormChangeDescription = (event) => {
    let description = event.target.value
    this.setState({
      form: {
        ...this.state.form,
        description: description
      }
    })
  }

  handleAddRecord = record => {
    const form = this.state.form
    this.setState({
      form: {
        ...form,
        records: [...form.records, record]
      }
    })
  }

  handleRemoveRecord = idx => {
    const form = this.state.form
    const records = [...form.records]
    records.splice(idx, 1)
    this.setState({
      form: {...form, records}
    })
  }

  handleEditRecord = (idx, record) => {
    const form = this.state.form
    const records = form.records
    records[idx] = record
    this.setState({
      form: {
        ...form,
        records: [...form.records]
      }
    })
  }

  handleSubmit = async () => {
    this.setState({
      validations: []
    })
    const {form} = this.state
    const validations = []
    if (form.records.length < 1) {
      validations.push('Nie można złożyć zamówienia bez żadnego rekordu')
    }

    if (validations.length > 0) {
      this.setState({
        validations: validations
      })
      return
    }

    const preparedForm = {
      ...form,
      status: form.status ? form.status : {id: 0},
      principal: form.principal.id,
      records: form.records.map(record => ({
        ...record,
        status: record.status ? record.status : {id: 0},
        settlementType: record.settlementType
      }))
    }

    form.id == null ?
        CommissionAPI.create(preparedForm)
            .then(response => {
              const status = response.status
              const data = response.data
              if (status !== 201) {
                this.setState({
                  validations: data.validationExceptions.map(ex => ex.message)
                })
              } else {
                this.props.history.replace('/board/commission/' + data.id)
              }
            })
        :
        CommissionAPI.edit(preparedForm)
            .then(response => {
              const status = response.status
              const data = response.data
              if (status !== 200) {
                this.setState({
                  validations: data.validationExceptions.map(ex => ex.message)
                })
              } else {
                this.props.history.replace('/board/commission/' + data.id)
              }
            })
  }

  render() {
    const user = this.state.user
    const isAdmin = user != null && user.authorities.some(auth => auth.id === 0)
    return (
        <>
          <Toolbar cancel={this.cancel}/>
          <Container>
            <div className='commission-new-form'>
              <CommissionDataForm
                  commissionId={this.state.form.id}
                  user={user}
                  principal={this.state.form.principal}

                  organisations={this.state.organisations}
                  organisation={this.state.form.organisation}
                  handleFormChangeOrganisation={this.handleFormChangeOrganisation}

                  locations={this.state.locations}
                  location={this.state.form.location}
                  handleFormChangeLocation={this.handleFormChangeLocation}

                  description={this.state.form.description}
                  handleFormChangeDescription={this.handleFormChangeDescription}
              />

              <RecordsList
                  isAdmin={isAdmin}
                  commission={this.state.form.id}
                  records={this.state.form.records}
                  addRecord={this.handleAddRecord}
                  editRecord={this.handleEditRecord}
                  removeRecord={this.handleRemoveRecord}
              />

              <ValidationErrors
                  validations={this.state.validations}
              />

              <Row>
                <Button type="submit" variant='info' className='mx-auto mt-3 col-3' onClick={this.handleSubmit}>
                  Zapisz
                </Button>
              </Row>

            </div>
          </Container>
        </>
    )
  }
}

const RecordsList = props => {
  const [recordToDelete, setRecordToDelete] = useState(null)
  const [showRecordAddForm, setShowRecordAddForm] = useState(false)
  const [showRecordEditForm, setShowRecordEditForm] = useState(false)
  const [recordToEdit, setRecordToEdit] = useState(null)

  const deleteRecord = (idx, record) => {
    setRecordToDelete([idx, record.id])
  }

  const addRecord = () => {
    setShowRecordAddForm(true)
  }

  const editRecord = (idx, record) => {
    setRecordToEdit([idx, record])
    setShowRecordEditForm(true)
  }

  return <>

    <CommissionRecordDeleteModal
        recordToDelete={recordToDelete}
        setRecordToDelete={setRecordToDelete}
        handleRemoveRecord={props.removeRecord}
    />
    <CommissionRecordDataFormModal
        show={showRecordAddForm}
        setShow={setShowRecordAddForm}
        isAdmin={props.isAdmin}
        commission={props.commission}
        record={null}
        setRecord={null}
        handleSaveRecord={props.addRecord}
    />
    <CommissionRecordDataFormModal
        show={showRecordEditForm}
        setShow={setShowRecordEditForm}
        isAdmin={props.isAdmin}
        commission={props.commission}
        record={recordToEdit}
        setRecord={setRecordToEdit}
        handleSaveRecord={props.editRecord}
    />

    <Card className='commission-new-card mt-3'>
      <Card.Header className='card-header-custom'>
        <b>Rekordy zamówienia</b>
        <OverlayTriggerIcon
            overlay='Dodaj'
            theme='commission-icon-white'
            icon={<RiAddLine onClick={addRecord}/>}
        />
      </Card.Header>
      {props.records.map((record, idx) => (
          <Accordion key={idx}>
            <Card>
              <Card.Header>
                <Card.Title className='mb-1'>
                  Rekord {record.id || '#' + (idx + 1)}
                </Card.Title>
                <Row className='mt-3' style={{fontSize: '1.2em'}}>
                  <Col xs={12} md={3} className='pt-xs-3'><b>Zamówiono:</b> {record.ordered}</Col>
                  <Col xs={12} md={6} className='pt-xs-3'><b>Stanowisko:</b> {record.jobName}</Col>
                  <Col sm={12} md={3} className='pt-xs-4'>
                    <OverlayTriggerIcon
                        overlay='Usuń'
                        icon={<RiDeleteBinLine onClick={() => deleteRecord(idx, record)}/>}
                    />
                    <OverlayTriggerIcon
                        overlay='Edytuj'
                        icon={<RiEditLine onClick={() => editRecord(idx, record)}/>}
                    />
                    <ShowHideToggle eventKey={idx + 1}/>
                  </Col>
                </Row>
              </Card.Header>
              <Accordion.Collapse eventKey={idx + 1}>
                <Card.Body>
                  <Container as={Row} className='p-0 m-auto w-100'>
                    <DetailsRow name='Pensja minimalna' value={record.wageRateMin}/>
                    <DetailsRow name='Pensja maksymalna' value={record.wageRateMax}/>
                    <DetailsRow
                        name='Data rozpoczęcia'
                        value={record.startDate}/>
                    <DetailsRow
                        name='Data zakończenia'
                        value={record.endDate}/>
                    <DetailsRow name='Rozliczenie' value={record.settlementType.desc}/>
                    <DetailsRow name='Uwagi' value={record.description}/>
                  </Container>
                </Card.Body>
              </Accordion.Collapse>
            </Card>
          </Accordion>
      ))}
    </Card>
  </>;
}

const ValidationErrors = props => {
  return <>
    {props.validations.map((validation, idx) => <ValidationError key={idx} validation={validation}/>)}
  </>
}

const ValidationError = props => {
  const [show, setShow] = useState(true)
  return (
      <Alert
          variant="danger"
          show={show}
          onClose={() => setShow(false)}
          className='mt-3'
          dismissible
      >
        <p>{props.validation}</p>
      </Alert>
  )
}

export default CommissionForm