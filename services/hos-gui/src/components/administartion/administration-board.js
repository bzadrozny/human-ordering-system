import React, {Component} from 'react';
import Toolbar from "../board/board-toolbar";
import {Card, Container, ListGroup, Row} from "react-bootstrap";
import DetailsRow from "../common/details-row";
import ShowHideToggle from "../common/show-hide-toggle";


class AdministrationBoard extends Component {

  render() {

    return <>
      <Toolbar
          create={() => ''}
      />
      <Container>
        <Card className='commission-details-card'>
          <Card.Header className='card-header-custom'>
            <b>Organizacje</b>
            <ShowHideToggle
                eventKey={0}
                className='commission-icon-white'
                fontSize={21}
            />
          </Card.Header>
          <ListGroup variant="flush">
            <ListGroup.Item>
              <Card.Title className='mb-1'>Szczegóły</Card.Title>
              <Container as={Row} className='p-0 m-auto w-100'>
                <DetailsRow name='Status' value='Aktywna'/>
                <DetailsRow name='Lokalizacja' value='asd'/>
                <DetailsRow name='Opis' value='asdas'/>
              </Container>
            </ListGroup.Item>
          </ListGroup>
        </Card>
      </Container>
    </>
  }

}

export default AdministrationBoard