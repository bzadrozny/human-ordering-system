import React, {useEffect, useState} from "react";
import {Button, Card, Col, Container, Form, Modal, Row} from "react-bootstrap";
import {CommissionAPI, UserAPI} from "../../../api/hos-service-api";
import DatePicker from "react-datepicker";
import DetailsRow from "../../common/details-row";
import ValidationErrors from "../../common/validation-errors";

const CommissionAcceptModal = props => {
  const show = props.show
  const close = () => {
    setValidations([])
    setError({description: ''})
    props.setShow(false)
  }

  const commission = props.commission
  const minRealisationDate = new Date()
  const maxRealisationDate = commission.records
      .map(rec => new Date(rec.startDate))
      .reduce((acc = Date.max, next) => acc > next ? next : acc)

  const [decision, setDecision] = useState({
    id: commission.id,
    decision: {id: 0},
    realisationDate: maxRealisationDate,
    executor: undefined,
    description: undefined
  })
  const [error, setError] = useState({
    description: ''
  })

  const [executors, setExecutors] = useState([])
  useEffect(() => {
    UserAPI.allManagers()
        .then(resp => resp.data)
        .then(executors => setExecutors([...executors]))
  }, [])

  const setRealisationDate = date => {
    setDecision({
      ...decision,
      realisationDate: date
    })
  }

  const setExecutor = event => {
    const value = event.target.value
    setDecision({
      ...decision,
      executor: value
    })
  }

  const setDescription = event => {
    const value = event.target.value
    if (value != null && value !== '' && (value.length < 5 || value.length > 250)) setError({
      description: "Pole musi mieć od 5 do 250 znaków"
    })
    else setError({
      description: ''
    })
    setDecision({
      ...decision,
      description: value
    })
  }

  const [validations, setValidations] = useState([])
  const acceptCommission = async () => {
    const validations = []
    if (decision.executor == null) {
      validations.push("Przed akceptają konieczne jest wybranie opiekuna zamówienia")
    }
    if (decision.realisationDate == null) {
      validations.push("Ackeptowana data realizacji jest wymagana")
    }
    setValidations(validations)
    if (validations.length) return

    const decisionForm = {...decision}
    decisionForm.realisationDate = decisionForm.realisationDate.toISOString().substr(0, 10)
    CommissionAPI.decision(decisionForm)
        .then(resp => {
          if (resp.status !== 200) {
            setValidations(resp.data.validationExceptions.map(ex => ex.message))
          } else {
            props.setCommission(resp.data)
            close()
          }
        })
  }

  return <Modal
      show={show}
      onHide={close}
      size="lg"
      backdrop='static'
      keyboard={false}
  >
    <Modal.Header closeButton>
      <Modal.Title>Akceptacja zamówienia {commission.id}</Modal.Title>
    </Modal.Header>
    <Modal.Body>

      <Form.Group as={Col} controlId="formDecision">
        <Form.Label>Decyzja</Form.Label>
        <Form.Control
            value='Akceptacja'
            disabled
        />
      </Form.Group>

      <Form.Group as={Col} controlId="formRealisationDate">
        <Form.Label>Data realizacji</Form.Label><br/>
        <Form.Control
            placeholder="Data realizacji"
            as={DatePicker}
            selected={decision.realisationDate}
            onChange={setRealisationDate}
            minDate={minRealisationDate}
            maxDate={maxRealisationDate}
            dateFormat='yyyy-MM-dd'
            required
        />
      </Form.Group>

      <Form.Group as={Col} controlId="formExecutor">
        <Form.Label>Opiekun</Form.Label>
        <Form.Control
            as="select"
            value={decision.executor}
            onChange={setExecutor}
            disabled={executors.length === 0}
        >
          <option/>
          {executors.length > 0 && executors.map(executor =>
              <option key={executor.id} value={executor.id}>{executor.email}</option>
          )}
        </Form.Control>
      </Form.Group>

      <Form.Group as={Col} controlId="formDescription">
        <Form.Label>Uwagi</Form.Label>
        <Form.Control
            as='textarea'
            value={decision.description}
            onChange={setDescription}
            maxLength={255}
        />
        <span style={{color: "red"}}>{error.description}</span>
      </Form.Group>

      {commission.records.map(record => (
          <Card key={record.id} className='my-3'>
            <Card.Header>
              <Card.Title className='mb-0'>
                {record.ordered} x {record.jobName.toUpperCase()}
              </Card.Title>
            </Card.Header>
            <Card.Body>
              <Container as={Row} className='p-0 m-auto w-100' style={{fontSize: '0.8em'}}>
                <DetailsRow name='Start' value={record.startDate}/>
                <DetailsRow name='Koniec' value={record.endDate}/>
                <DetailsRow name='Pensja minimalna' value={record.wageRateMin + 'zł'}/>
                <DetailsRow name='Forma rozliczenia' value={record.settlementType.desc}/>
              </Container>
            </Card.Body>
          </Card>
      ))}

      <ValidationErrors
          validations={validations}
      />

    </Modal.Body>
    <Modal.Footer>
      <div className='mx-auto my-2'>
        Uwaga! Operacja jest nieodwracalna i zobowiązuje do realizacji zamówienia na wskaznaych warunkach.
      </div>
      <Button variant="secondary" onClick={close}>
        Anuluj
      </Button>
      <Button variant="primary" onClick={acceptCommission}>
        Zaakceptuj
      </Button>
    </Modal.Footer>
  </Modal>
}

export default CommissionAcceptModal