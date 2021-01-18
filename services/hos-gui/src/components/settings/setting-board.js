import React, {Component} from 'react';
import Toolbar from "../board/board-toolbar";
import {Button, Card, Col, Container, Form, InputGroup, ListGroup, Modal, Row} from "react-bootstrap";
import DetailsRow from "../common/details-row";


class SettingBoard extends Component {

  constructor(props) {
    super(props);
    this.state = {
      changePass: false
    }
  }

  render() {

    return <>
      <Toolbar
          changePass={() => this.setState({changePass: true})}
      />

      <Modal
          show={this.state.changePass}
          onHide={() => this.setState({changePass: false})}
          size='lg'
          backdrop='static'
          keyboard={false}
      >
        <Modal.Header closeButton>
          <Modal.Title>Zmiana hasła</Modal.Title>
        </Modal.Header>
        <Modal.Body>

          <Form.Row>
            <Form.Group as={Col} controlId="formCode">
              <Form.Label>Aktualne hasło</Form.Label>
              <Form.Control
                  type='password'
                  placeholder="Aktualne hasło"
              />
            </Form.Group>
          </Form.Row>
          <Form.Row>
            <Form.Group as={Col} controlId="formCode">
              <Form.Label>Nowe hasło</Form.Label>
              <InputGroup>
                <Form.Control
                    type='password'
                    placeholder="Nowe hasło"
                />
                <InputGroup.Append>
                  <InputGroup.Text style={{backgroundColor: 'green', color: 'white'}}>OK</InputGroup.Text>
                </InputGroup.Append>
              </InputGroup>
            </Form.Group>
          </Form.Row>
          <Form.Row>
            <Form.Group as={Col} controlId="formCode">
              <Form.Label>Potwierdź nowe hasło</Form.Label>
              <Form.Control
                  type='password'
                  placeholder="Potwierdź nowe hasło"
              />
              <span style={{color: "red"}}>Hasła nie są identyczne</span>
            </Form.Group>
          </Form.Row>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => this.setState({changePass: false})}>
            Anuluj
          </Button>
          <Button variant="primary">
            Zmień
          </Button>
        </Modal.Footer>
      </Modal>

      <Container>
        <Card className='commission-details-card'>
          <Card.Header className='card-header-custom'>
            <Card.Title><b>Dane użytkownika: recruiter021</b></Card.Title>
          </Card.Header>
          <Card>
            <ListGroup variant="flush">
              <ListGroup.Item>
                <Container as={Row} className='p-0 m-auto w-100'>
                  <DetailsRow name='Imię' value='Rekruter'/>
                  <DetailsRow name='Nazwisko' value='Skuteczny'/>
                  <DetailsRow name='Email' value='recruiter@mail.com'/>
                  <DetailsRow name='Telefon' value='999-888-777'/>
                </Container>
              </ListGroup.Item>
            </ListGroup>
          </Card>

          <Card.Header className='card-header-custom'>
            <Card.Title style={{fontSize: '1em', height: '0.5em'}}>Organizacja</Card.Title>
          </Card.Header>
          <Card>
            <ListGroup variant="flush">
              <ListGroup.Item>
                <Container as={Row} className='p-0 m-auto w-100'>
                  <DetailsRow name='Nazwa' value='Buffalo project'/>
                  <DetailsRow name='Nip' value='525-000-58-34'/>
                  <DetailsRow name='Status' value='Aktywna'/>
                </Container>
              </ListGroup.Item>

              <Card.Title className='ml-3 my-3' style={{fontSize: '1em', height: '0.5em'}}>
                Dział
              </Card.Title>
              <ListGroup.Item>
                <Container as={Row} className='p-0 m-auto w-100'>
                  <DetailsRow name='Nazwa' value='HR'/>
                  <DetailsRow name='Dyrektor' value='Mariusz Darko'/>
                  <DetailsRow name='Status' value='Aktywna'/>
                </Container>
              </ListGroup.Item>

              <Card.Title className='ml-3 my-3' style={{fontSize: '1em', height: '0.5em'}}>
                Lokalizacja
              </Card.Title>
              <ListGroup.Item>
                <Container as={Row} className='p-0 m-auto w-100'>
                  <DetailsRow name='Nazwa' value='Biuro Spire HOS'/>
                  <DetailsRow name='Główne biuro' value='TAK'/>
                </Container>
              </ListGroup.Item>

              <Card.Title className='ml-3 my-3' style={{fontSize: '1em', height: '0.5em'}}>
                Adres
              </Card.Title>
              <ListGroup.Item>
                <Container as={Row} className='p-0 m-auto w-100'>
                  <DetailsRow name='Miasto' value='Warszawa'/>
                  <DetailsRow name='Kod pocztowy' value='01-001'/>
                  <DetailsRow name='Ulica' value='Werbey'/>
                  <DetailsRow name='Numer' value='81'/>
                </Container>
              </ListGroup.Item>
            </ListGroup>
          </Card>

          <Card.Header className='card-header-custom'>
            <Card.Title style={{fontSize: '1em', height: '0.5em'}}>Uprawnienia</Card.Title>
          </Card.Header>
          <ListGroup.Item>
            <Container as={Row} className='p-0 m-auto w-100'>
              <Container> - Rekruter</Container>
            </Container>
          </ListGroup.Item>
        </Card>
      </Container>
    </>
  }

}

export default SettingBoard