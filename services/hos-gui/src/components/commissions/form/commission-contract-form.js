import {Accordion, Button, Card, Col, Form, InputGroup, Row, Table} from "react-bootstrap";
import React, {useContext, useState} from "react";
import DatePicker from "react-datepicker";
import ValidationErrors from "../../common/validation-errors";
import ShowHideToggle from "../../common/show-hide-toggle";
import {RiMoreFill, RiUserFollowLine} from "react-icons/ri";
import OverlayTriggerIcon from "../../common/overlay-trigger-icon";
import {dateToString} from "../../../services/utils/common-utils";
import UserContext from "../../../context/user-context";

const CommissionContractForm = props => {
  const user = useContext(UserContext)
  const {
    contract,
    contractRecord,
    commissionId,
    handleSubmit,
    restValidation
  } = {...props}

  const [code, setCode] = useState((contract && contract.code) || '')
  const [candidate, setCandidate] = useState(undefined)
  const [contractDate, setContractDate] = useState((contract && new Date(contract.contractDate)) || undefined)
  const [startDate, setStartDate] = useState(
      (contract && new Date(contract.startDate))
      || new Date(contractRecord.startDate)
      || undefined
  )
  const [endDate, setEndDate] = useState(
      (contract && contract.endDate && new Date(contract.endDate))
      || (contractRecord.endDate && new Date(contractRecord.endDate))
      || undefined
  )
  const [salary, setSalary] = useState((contract && contract.salary) || undefined)
  const [contractType, setContractType] = useState((contract && contract.contractType.id) || 0)
  const [description, setDescription] = useState((contract && contract.description) || undefined)
  const [validations, setValidations] = useState({
    code: '',
    contractDate: '',
    startDate: '',
    salary: ''
  })

  const [candidates, setCandidates] = useState([])
  const findCandidate = () => {
    setCandidates([
      {id: 1021, name: 'Andrzej', surname: 'Sołtys', email: 'andrzej.soltys@mail.com'},
      {id: 1025, name: 'Andrzej', surname: 'Sołtys', email: 'andrzej.soltys01@mail.com'}
    ])
  }

  function validateField(fields) {
    let hasError = false
    const validation = {...validations}
    fields.forEach(({field, value}) => {
      switch (field) {
        case "code":
          if (value == null || value.length < 5 || value.length > 20) {
            validation.code = "Pole jest wymagane i musi mieć od 5 do 20 znaków"
            hasError = true
          } else {
            validation.code = ''
          }
          break
        case "contractDate":
          if (value == null) {
            validation.contractDate = "Pole jest wymagane"
            hasError = true
          } else {
            validation.contractDate = ''
          }
          break
        case "startDate":
          if (value == null) {
            validation.startDate = "Pole jest wymagane"
            hasError = true
          } else {
            validation.startDate = ''
          }
          break
        case "salary":
          if (value == null || value === '' || value < contractRecord.wageRateMin || value > contractRecord.wageRateMax) {
            if (contractRecord.wageRateMin === contractRecord.wageRateMax) {
              validation.salary = "Pensja jest wymagana i musi być równa kwocie określonej przez klienta: " + contractRecord.wageRateMin + "zł"
            } else {
              validation.salary = "Pensja jest wymagana i musi mieścić się w zakresie określonym przez klienta: " + contractRecord.wageRateMin + " - " + contractRecord.wageRateMax
            }
            hasError = true
          } else {
            validation.salary = ''
          }
          break
        default:
          break
      }
    })
    setValidations({...validation})
    return hasError
  }

  const handleSaveContract = () => {
    let hasError = validateField([
      {field: 'code', value: code},
      {field: 'contractDate', value: contractDate},
      {field: 'startDate', value: startDate},
      {field: 'endDate', value: endDate},
      {field: 'endDate', value: endDate},
      {field: 'salary', value: salary}
    ])
    if (hasError) {
      return
    }

    const contractGeneralForm = {
      commission: commissionId,
      commissionRecord: contractRecord.id,
      code: code,
      contractDate: dateToString(contractDate),
      startDate: dateToString(startDate),
      endDate: dateToString(endDate),
      salary: salary,
      contractType: {id: contractType},
      description: description,
      candidate: undefined
    }
    const contractForm = contract ? {
      id: contract.id,
      recruiter: contract.recruiter.id,
      ...contractGeneralForm,
    } : {
      id: undefined,
      recruiter: user.id,
      ...contractGeneralForm,
    }
    handleSubmit(contractForm)
  }

  return <>
    <Form.Row>
      <Form.Group as={Col} controlId="formCode">
        <Form.Label>Kod kontraktu</Form.Label>
        <Form.Control
            type='text'
            placeholder="Kod kontraktu"
            value={code}
            onChange={e => {
              const value = e.target.value
              validateField([{field: 'code', value}])
              setCode(value)
            }}
        />
        <span style={{color: "red"}}>{validations.code}</span>
      </Form.Group>
    </Form.Row>

    <Form.Row>
      <Form.Group as={Col} controlId="formCandidate">
        <Form.Label>Kandydat</Form.Label>
        <Form.Control
            type='text'
            placeholder="Wybierz kandydata"
            value={candidate != null ? (candidate.id + ': ' + candidate.name + ' ' + candidate.surname) : undefined}
            disabled
        />
        <span style={{color: "red"}}>{validations.candidate}</span>
      </Form.Group>
    </Form.Row>

    <Accordion>
      <Card className='mb-3'>
        <Card.Header className='pb-0'>
          <Row>
            <Col sm={11}>Wyszukaj kandydata</Col>
            <Col sm={1}>
              <ShowHideToggle eventKey={1}/>
            </Col>
          </Row>
        </Card.Header>
        <Accordion.Collapse eventKey={1}>
          <Card.Body>
            <Form.Label>Podaj imię i nazwisko</Form.Label>
            <InputGroup className="mb-3">
              <Form.Control
                  placeholder="Imię"

              />
              <Form.Control
                  placeholder="Nazwisko"

              />
              <InputGroup.Append>
                <Button onClick={() => findCandidate()} variant='outline-info'>
                  Szukaj
                </Button>
              </InputGroup.Append>
            </InputGroup>
            <Table className='my-3 mx-0' size="sm" responsive="md" striped hover>
              <thead>
              <tr>
                <th>ID</th>
                <th>Imię</th>
                <th>Nazwisko</th>
                <th>Email</th>
                <th><RiMoreFill/></th>
              </tr>
              </thead>
              <tbody>
              {candidates.length === 0 ? (
                  <tr>
                    <td colSpan="5">Brak kandydatów dla podanych dnaych</td>
                  </tr>
              ) : candidates.map(candidate => (
                  <tr key={candidate.id}>
                    <td>{candidate.id}</td>
                    <td>{candidate.name}</td>
                    <td>{candidate.surname}</td>
                    <td>{candidate.email}</td>
                    <td width={21}>
                      <OverlayTriggerIcon
                          overlay='Wybierz'
                          theme='commission-icon-table'
                          icon={
                            <RiUserFollowLine
                                onClick={() => {
                                  setCandidates([])
                                  setCandidate(candidate)
                                }}
                                fontSize={21}
                            />
                          }
                      />
                    </td>
                  </tr>
              ))}
              </tbody>
            </Table>
          </Card.Body>
        </Accordion.Collapse>
      </Card>
    </Accordion>

    <Form.Row>
      <Form.Group as={Col} controlId="formContractDate">
        <Form.Label>Data umowy</Form.Label>
        <br/>
        <Form.Control
            placeholder="Data kontraktu"
            autocomplete="off"
            as={DatePicker}
            selected={contractDate}
            onChange={date => {
              validateField([{field: 'contractDate', value: date}])
              setContractDate(date)
              date > startDate && setStartDate(undefined) && setEndDate(undefined)
            }}
            dateFormat='yyyy-MM-dd'
            required
        />
        <br/>
        <span style={{color: "red"}}>{validations.contractDate}</span>
      </Form.Group>
      <Form.Group as={Col} controlId="formStartDate">
        <Form.Label>Data rozpoczęcia</Form.Label>
        <br/>
        <Form.Control
            placeholder="Data rozpoczęcia"
            autocomplete="off"
            as={DatePicker}
            selected={startDate}
            onChange={date => {
              validateField([{field: 'startDate', value: date}])
              setStartDate(date)
              date > endDate && setEndDate(undefined)
            }}
            minDate={contractDate}
            dateFormat='yyyy-MM-dd'
            required
        />
        <br/>
        <span style={{color: "red"}}>{validations.startDate}</span>
      </Form.Group>
      <Form.Group as={Col} controlId="formEndDate">
        <Form.Label>Data zakończenia</Form.Label>
        <br/>
        <Form.Control
            placeholder="Data zakończenia"
            autocomplete="off"
            as={DatePicker}
            selected={endDate}
            onChange={date => setEndDate(date)}
            minDate={startDate}
            dateFormat='yyyy-MM-dd'
            required
        />
        <br/>
      </Form.Group>
    </Form.Row>

    <Form.Row>
      <Form.Group as={Col} controlId="formSalary">
        <Form.Label>Pensja {contractRecord.settlementType.desc}</Form.Label>
        <InputGroup>
          <Form.Control
              type='number'
              placeholder="Pensja"
              value={salary}
              onChange={e => {
                const value = e.target.value
                validateField([{field: 'salary', value}])
                setSalary(value)
              }}
              min={contractRecord.wageRateMin}
              max={contractRecord.wageRateMax}
              required
          />
          <InputGroup.Append>
            <InputGroup.Text>zł</InputGroup.Text>
          </InputGroup.Append>
        </InputGroup>
        <span style={{color: "red"}}>{validations.salary}</span>
      </Form.Group>
      <Form.Group as={Col} controlId="formContractType">
        <Form.Label>Typ umowy</Form.Label>
        <Form.Control
            as='select'
            value={contractType}
            onChange={e => setContractType(e.target.value)}
            required
        >
          <option value={0}>Umowa o pracę</option>
          <option value={1}>Umowa zlecenie</option>
        </Form.Control>
      </Form.Group>
    </Form.Row>

    <Form.Group as={Col} controlId="formDescription">
      <Form.Label>Uwagi</Form.Label>
      <Form.Control
          as='textarea'
          placeholder="Uwagi"
          value={description}
          onChange={e => {
            const value = e.target.value
            setDescription(value === '' ? undefined : value)
          }}
          maxLength={255}
      />
    </Form.Group>

    <ValidationErrors
        validations={restValidation ? restValidation.map(ex => ex.message) : []}
    />

    <Row>
      <Button onClick={() => handleSaveContract()} variant='outline-info' className='mx-auto mt-3 mb-3 col-3'>
        {contract ? 'Zatwierdź' : 'Dodaj kontrakt'}
      </Button>
    </Row>

  </>

}

export default CommissionContractForm