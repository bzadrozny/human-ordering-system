import React from "react";
import {Card, Col, Form} from "react-bootstrap";

const CommissionDataForm = (props) => {
  return <Card className='commission-new-card'>
    <Card.Header className='card-header-custom'>
      <b>{props.commissionId ? 'Edycja zlecenia: ' + props.commissionId : 'Nowe zlecenie'}</b>
    </Card.Header>
    <br/>
    <Form.Group controlId="formPrincipal">
      <Form.Label>Zamawiający</Form.Label>
      <Form.Control
          value={(props.principal && props.principal.email) || (props.user && props.user.email)}
          onChange={null}
          disabled/>
    </Form.Group>

    <Form.Row>
      <Form.Group as={Col} controlId="formOrganisation">
        <Form.Label>Organizacja</Form.Label>
        <Form.Control
            as="select"
            value={props.organisation}
            onChange={props.handleFormChangeOrganisation}
            disabled={props.principal != null && props.principal.id !== props.user.id}
        >
          {props.organisations && props.organisations.map(org =>
              <option key={org.id} value={org.id}>{org.name}</option>
          )}
        </Form.Control>
      </Form.Group>

      <Form.Group as={Col} controlId="formLocation">
        <Form.Label>Lokalizacja</Form.Label>
        <Form.Control
            as="select"
            value={props.location}
            onChange={props.handleFormChangeLocation}
        >
          {props.locations && props.locations.map(org =>
              <option key={org.id} value={org.id}>{org.name}</option>
          )}
        </Form.Control>
      </Form.Group>
    </Form.Row>

    <Form.Group as={Col} controlId="formDescription">
      <Form.Label>Uwagi</Form.Label>
      <Form.Control
          as='textarea'
          value={props.description}
          onChange={props.handleFormChangeDescription}
          placeholder="Uwagi"
          maxLength={255}/>
    </Form.Group>
  </Card>
}

export default CommissionDataForm