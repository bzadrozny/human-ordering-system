import {Button, Card, Col, Container, Form, Modal, Row} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import {CommissionAPI, UserAPI} from "../../../api/hos-service-api";
import DetailsRow from "../../common/details-row";
import DatePicker from "react-datepicker";
import ValidationErrors from "../../common/validation-errors";

const CommissionRejectModal = props => {
  const show = props.show
  const close = () => {
    setValidations([])
    props.setShow(false)
    setError({
      executor: '',
      description: '',
      records: commission.records.map(rec => ({
        id: rec.id,
        accepted: '',
        startDate: ''
      }))
    })
  }

  const commission = props.commission
  const [decision, setDecision] = useState({
    id: commission.id,
    decision: {id: 1},
    executor: undefined,
    description: undefined,
    records: [...commission.records.map(rec => ({
      id: rec.id,
      decision: 0,
      accepted: undefined,
      startDate: undefined
    }))]
  })
  const [error, setError] = useState({
    executor: '',
    description: '',
    records: commission.records.map(rec => ({
      id: rec.id,
      accepted: '',
      startDate: ''
    }))
  })

  const [executors, setExecutors] = useState([])
  useEffect(() => {
    UserAPI.allManagers()
        .then(resp => resp.data)
        .then(executors => setExecutors([...executors]))
  }, [])

  const setExecutor = event => {
    const value = event.target.value
    setError({
      ...error,
      executor: (value == null || value === '') ? 'Przed wysłaniem konieczne jest wybranie opiekuna zamówienia' : ''
    })
    setDecision({
      ...decision,
      executor: value
    })
  }

  const setDescription = event => {
    const value = event.target.value
    setError({
      ...error,
      description: (value == null || value.length < 5 || value.length > 250) ?
          'Pole jest wymagane i musi mieć od 5 do 250 znaków' : ''
    })
    setDecision({
      ...decision,
      description: value
    })
  }

  const setRecordDecision = (id, recordDecision) => {
    const records = [...decision.records]
    const record = records.find(rec => id === rec.id)
    record.decision = recordDecision
    if (recordDecision !== 1) {
      record.accepted = undefined
      record.startDate = undefined
    }
    setDecision({
      ...decision,
      records: [...records]
    })
  }

  const setRecordAccepted = (id, recordAccepted) => {
    const record = commission.records.find(rec => id === rec.id)
    const recordsError = [...error.records]
    recordsError.find(rec => id === rec.id).accepted = recordAccepted < 1 || recordAccepted > record.ordered ?
        'Zaakceptowana ilość jest wymagana i ilość musi mieścić się w zakresie 1 : ' + record.ordered :
        ''
    setError({
      ...error,
      records: [...recordsError]
    })
    const records = [...decision.records]
    records.find(rec => id === rec.id).accepted = recordAccepted
    setDecision({
      ...decision,
      records: [...records]
    })
  }

  const setRecordStartDate = (id, recordStartDate) => {
    const record = commission.records.find(rec => id === rec.id)
    const recordsError = [...error.records]
    recordsError.find(rec => id === rec.id).startDate = recordStartDate == null || recordStartDate < record.startDate ?
        'Zaakceptowana data jest wymagana i nie może być wcześniejsza niż data zamówienia' :
        ''
    setError({
      ...error,
      records: [...recordsError]
    })
    const records = [...decision.records]
    records.find(rec => id === rec.id).startDate = recordStartDate
    setDecision({
      ...decision,
      records: [...records]
    })
  }

  const [validations, setValidations] = useState([])
  const rejectCommission = () => {
    const executorValidation = (decision.executor == null || decision.executor === '') ?
        'Przed wysłaniem konieczne jest wybranie opiekuna zamówienia' : ''
    const descriptionValidation = (decision.description == null || decision.description.length < 5 || decision.description.length > 250) ?
        'Pole jest wymagane i musi mieć od 5 do 250 znaków' : ''
    const recordsValidation = decision.records.map(rec => {
      const record = commission.records.find(r => rec.id === r.id)
      return ({
        id: rec.id,
        accepted: rec.decision === 1 && (rec.accepted == null || rec.accepted < 1 || rec.accepted > record.ordered) ?
            'Zaakceptowana ilość jest wymagana i musi mieścić się w zakresie 1 : ' + record.ordered :
            '',
        startDate: rec.decision === 1 && (rec.startDate == null || rec.startDate < record.startDate) ?
            'Zaakceptowana data jest wymagana i nie może być wcześniejsza niż data zamówienia' :
            ''
      })
    })
    const hasExecutorValidation = executorValidation !== ''
    const hasDescriptionValidation = descriptionValidation !== ''
    const hasRecordsValidation = recordsValidation.some(rec => rec.accepted !== '' || rec.startDate !== '')
    if (hasExecutorValidation || hasDescriptionValidation || hasRecordsValidation) {
      setError({
        executor: executorValidation,
        description: descriptionValidation,
        records: [...recordsValidation]
      })
      return
    }
    const decisionForm = {...decision}
    console.log(decisionForm)
    decisionForm.executor = parseInt(decisionForm.executor)
    decisionForm.records = decisionForm.records.map(rec => ({
      id: rec.id,
      decision: {id: rec.decision},
      accepted: rec.accepted,
      startDate: rec.startDate != null ? rec.startDate.toISOString().substr(0, 10) : null,
    }))
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
      <Modal.Title>Zastrzeż zamówienie</Modal.Title>
    </Modal.Header>
    <Modal.Body>

      <Form.Group as={Col} controlId="formDecision">
        <Form.Label>Decyzja</Form.Label>
        <Form.Control
            value='Zastrzeżenie'
            disabled
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
          <option value={undefined}/>
          {executors.length > 0 && executors.map(executor =>
              <option key={executor.id} value={executor.id}>{executor.email}</option>
          )}
        </Form.Control>
        <span style={{color: "red"}}>{error.executor}</span>
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

      {commission.records.map(record => {
        const recordDecision = decision.records.find(rec => rec.id === record.id)
        const minDate = new Date(record.startDate)
        return (
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

                <Container className='p-0 mx-auto mt-3 w-100' style={{fontSize: '0.8em'}}>

                  <Form.Group controlId={"formDecision" + record.id}>
                    <Form.Label>Decyzja:</Form.Label>
                    <Form.Control
                        as="select"
                        value={recordDecision.decision}
                        onChange={e => setRecordDecision(recordDecision.id, parseInt(e.target.value))}
                    >
                      <option value={0}>Akceptacja</option>
                      <option value={1}>Zatrzeżenie</option>
                      <option value={2}>Odrzucenie</option>
                    </Form.Control>
                  </Form.Group>

                  {recordDecision.decision === 1 && <Form.Row>
                    <Form.Group as={Col} md={6} lg={4} controlId="formAccepted">
                      <Form.Label>Ilość:</Form.Label>
                      <Form.Control
                          type='number'
                          placeholder="Ilość"
                          value={recordDecision.accepted}
                          onChange={e => setRecordAccepted(recordDecision.id, e.target.value)}
                          min={1}
                          max={record.ordered}
                      />
                      <span style={{color: "red"}}>{error.records.find(rec => record.id === rec.id).accepted}</span>
                    </Form.Group>
                    <Form.Group as={Col} controlId="formStartDate">
                      <Form.Label>Data rozpoczęcia:</Form.Label><br/>
                      <Form.Control
                          placeholder="Data rozpoczęcia"
                          as={DatePicker}
                          selected={recordDecision.startDate}
                          onChange={date => setRecordStartDate(recordDecision.id, date)}
                          minDate={minDate}
                          dateFormat='yyyy-MM-dd'
                      />
                      <span style={{color: "red"}}>{error.records.find(rec => record.id === rec.id).startDate}</span>
                    </Form.Group>
                  </Form.Row>}

                </Container>

              </Card.Body>
            </Card>
        )
      })}

      <ValidationErrors
          validations={validations}
      />

    </Modal.Body>
    <Modal.Footer>
      <div className='mx-auto my-2'>
        Uwaga! Operacja jest nieodwracalna i może decydować o dalszym procesowaniu zamówienia przez klienta.
      </div>
      <Button variant="secondary" onClick={close}>
        Anuluj
      </Button>
      <Button variant="primary" onClick={rejectCommission}>
        Zastrzeż
      </Button>
    </Modal.Footer>
  </Modal>
}

export default CommissionRejectModal