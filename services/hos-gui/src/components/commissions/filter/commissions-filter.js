import React, {Component} from "react";
import {Accordion, Button, Card, Col, Form, InputGroup, Row} from "react-bootstrap";
import {OrganisationApi} from '../../../api/hos-service-api'

export default class CommissionsFilter extends Component {

  constructor(props) {
    super(props);
    this.state = {
      id: undefined,
      status: undefined,
      organisation: undefined,
      organisations: [],
      location: undefined,
      locations: []
    }
  }

  componentDidMount = () => {
    OrganisationApi.allOrganisations()
        .then(resp => resp.data)
        .then(organisations =>
            this.setState({organisations: organisations})
        )
  }

  setId = (event) => {
    let value = event.target.value
    this.setState({
      id: value === '...' ? undefined : value,
      status: undefined,
      organisation: undefined,
      location: undefined
    })
  }

  setStatus = (event) => {
    let value = event.target.value
    this.setState({
      id: undefined,
      status: value === '...' ? undefined : value,
    })
  }

  setOrganisation = async (event) => {
    let organisation = event.target.value
    this.setState({
      id: undefined,
      organisation: organisation === '...' ? undefined : organisation,
    })

    let locations = organisation !== '...' ? await OrganisationApi.organisationById(organisation)
            .then(resp => resp.data)
            .then(organisation => organisation.locations)
        : []

    this.setState({
      locations: locations
    })
  }

  setLocation = (event) => {
    let value = event.target.value
    this.setState({
      id: undefined,
      location: value === '...' ? undefined : value
    })
  }

  filter = (event) => {
    event.preventDefault()
    let {id, status, organisation, location} = this.state
    this.props.handleFilter({id, status, organisation, location})
  }

  render() {
    const locationsEmpty = this.state.locations.length === 0
    return (
        <Accordion className='col-9 mx-auto' defaultActiveKey="0">
          <Card>
            <Accordion.Toggle as={Card.Header} eventKey="0">
              Znajdź po identyfikatorze zamówienia
            </Accordion.Toggle>
            <Accordion.Collapse eventKey="0">
              <Card.Body>
                <Form onSubmit={this.filter}>
                  <InputGroup className="mb-3">
                    <Form.Control
                        placeholder="Identyfikator"
                        aria-label="Identyfikator"
                        aria-describedby="basic-addon2"
                        onChange={this.setId}
                        value={this.state.id}
                    />
                    <InputGroup.Append>
                      <Button type="submit" variant="info">
                        Szukaj
                      </Button>
                    </InputGroup.Append>
                  </InputGroup>
                </Form>
              </Card.Body>
            </Accordion.Collapse>
          </Card>

          <Card>
            <Accordion.Toggle as={Card.Header} eventKey="1">
              Filtruj po parametrach
            </Accordion.Toggle>
            <Accordion.Collapse eventKey="1">
              <Card.Body>
                <Form onSubmit={this.filter}>
                  <Form.Row>
                    <Form.Group as={Col}>
                      <Form.Label htmlFor="filter-id">
                        Status
                      </Form.Label>
                      <Form.Control as="select" defaultValue={undefined} onChange={this.setStatus}>
                        <option value={undefined}>...</option>
                        <option value='CREATED'>Utworzono</option>
                        <option value='MODIFIED'>Zmodyfikowano</option>
                        <option value='SENT'>Wysłano</option>
                        <option value='REJECTED'>Zastrzeżono</option>
                        <option value='DELETED'>Anulowano</option>
                        <option value='EXECUTION'>Realizacja</option>
                        <option value='COMPLETED'>Zakończone</option>
                      </Form.Control>
                    </Form.Group>
                  </Form.Row>

                  <Form.Row>
                    <Form.Group as={Col} controlId="formGridCity">
                      <Form.Label>Organizacja</Form.Label>
                      <Form.Control
                          as="select"
                          defaultValue={this.state.organisation}
                          onChange={this.setOrganisation}
                      >
                        <option value={undefined}>...</option>
                        {this.state.organisations.map(org => <option key={org.id} value={org.id}>{org.name}</option>)}
                      </Form.Control>
                    </Form.Group>

                    <Form.Group as={Col} controlId="formGridState">
                      <Form.Label>Lokalizacja</Form.Label>
                      <Form.Control
                          as="select"
                          defaultValue={this.state.location}
                          onChange={this.setLocation}
                          disabled={locationsEmpty}
                      >
                        <option value={undefined}>{locationsEmpty ? "Wybierz organizację" : "..."}</option>
                        {this.state.locations.map(loc => <option key={loc.id} value={loc.id}>{loc.name}</option>)}
                      </Form.Control>
                    </Form.Group>
                  </Form.Row>

                  <Row>
                    <Button type="submit" variant='info' className="mx-auto">
                      Szukaj
                    </Button>
                  </Row>

                </Form>
              </Card.Body>
            </Accordion.Collapse>
          </Card>
        </Accordion>
    )
  }
}