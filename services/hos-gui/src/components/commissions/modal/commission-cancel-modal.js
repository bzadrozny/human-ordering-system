import {Button, Col, Form, Modal} from "react-bootstrap";
import React, {useContext, useState} from "react";
import UserContext from "../../../context/user-context";
import {CommissionAPI} from "../../../api/hos-service-api";
import ValidationErrors from "../../common/validation-errors";

const CommissionCancelModal = props => {
  const show = props.show
  const close = () => {
    setValidations([])
    props.setShow(false)
    setError({description: ''})
  }

  const user = useContext(UserContext)
  const commission = props.commission
  const [decision, setDecision] = useState({
    id: commission.id,
    decision: {id: 2},
    executor: user.id,
    description: undefined
  })
  const [error, setError] = useState({description: ''})

  const setDescription = event => {
    const value = event.target.value
    if (value == null || value.length < 5 || value.length > 250) setError({
      description: "Pole jest wymagane i musi mieć od 5 do 250 znaków"
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
  const cancelCommission = () => {
    const description = decision.description
    if (description == null || description.length < 5 || description.length > 250) {
      setError({
        description: "Pole jest wymagane i musi mieć od 5 do 250 znaków"
      })
      return;
    }
    CommissionAPI.decision(decision)
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
      <Modal.Title>Potwierdzenie odrzucenia zamówienia</Modal.Title>
    </Modal.Header>
    <Modal.Body>

      <Form.Group as={Col} controlId="formDecision">
        <Form.Label>Odrzucenie</Form.Label>
        <Form.Control
            value='Akceptacja'
            disabled
        />
      </Form.Group>

      <Form.Group as={Col} controlId="formExecutor">
        <Form.Label>Opiekun</Form.Label>
        <Form.Control
            as="select"
            value={decision.executor}
            disabled
        >
          <option value={user.id}>{user.email}</option>
        </Form.Control>
      </Form.Group>

      <Form.Group as={Col} controlId="formDescription">
        <Form.Label>Uwagi</Form.Label>
        <Form.Control
            as='textarea'
            value={decision.description}
            onChange={setDescription}
            minLength={5}
            maxLength={255}
        />
        <span style={{color: "red"}}>{error.description}</span>
      </Form.Group>

      <ValidationErrors
          validations={validations}
      />

    </Modal.Body>
    <Modal.Footer>
      <div className='mx-auto my-2'>
        Uwaga! Operacja jest nieodwracalna. Przywrócenie zamówienia będzie możliwe poprzez dodanie nowego zamówienia.
      </div>
      <Button variant="secondary" onClick={close}>
        Anuluj
      </Button>
      <Button variant="primary" onClick={cancelCommission}>
        Odrzuć
      </Button>
    </Modal.Footer>
  </Modal>
}

export default CommissionCancelModal