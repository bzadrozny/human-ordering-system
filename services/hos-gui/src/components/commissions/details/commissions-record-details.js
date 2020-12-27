import React from 'react'
import {Accordion, Card, Col, Container, Row} from "react-bootstrap";
import DetailsRow from "../../common/details-row";
import ShowHideToggle from "../../common/show-hide-toggle";

const CommissionRecordDetails = (props) => {
  const records = props.records
  const commissionStatus = props.status

  return (
      <Card className='mt-3 mb-4 commission-details-card'>
        <Card.Header className='card-header-custom'><b>Rekordy zamówienia</b></Card.Header>
        {records.map(record => (
                <Accordion key={record.id}>
                  <Card>
                    <Card.Header>
                      <Card.Title className='mb-1'>
                        Rekord: {record.id}
                      </Card.Title>
                      <Row className='mt-3' style={{fontSize: '1.2em'}}>
                        <Col xs={12} md={2} className='pt-xs-3'><b>Zamówiono:</b> {record.ordered}</Col>
                        <Col xs={12} md={2} className='pt-xs-3'><b>Status:</b> {record.status.desc}</Col>
                        <Col xs={12} md={7} className='pt-xs-3'><b>Stanowisko:</b> {record.jobName}</Col>
                        <Col sm={12} md={1} className='pt-xs-4'>
                          <ShowHideToggle eventKey={record.id}/>
                        </Col>
                      </Row>
                    </Card.Header>
                    <Accordion.Collapse eventKey={record.id}>
                      <Card.Body>
                        <Container as={Row} className='p-0 m-auto w-100'>
                          <DetailsRow name='Pensja minimalna' value={record.wageRateMin}/>
                          <DetailsRow name='Pensja maksymalna' value={record.wageRateMax}/>
                          <DetailsRow name='Data rozpoczęcia' value={record.startDate}/>
                          <DetailsRow name='Data zakończenia' value={record.endDate}/>
                          <DetailsRow name='Rozliczenie' value={record.settlementType.desc}/>
                          <DetailsRow name='Uwagi' value={record.description}/>
                        </Container>

                        <br/>
                        <RealisationCard
                            status={record.status}
                            acceptedOrdered={record.acceptedOrdered}
                            acceptedStartDate={record.acceptedStartDate}
                            recruited={record.recruited}
                        />

                      </Card.Body>
                    </Accordion.Collapse>
                  </Card>
                </Accordion>
            )
        )}
      </Card>
  )
}

const RealisationCard = props => {
  if (props.status.id === 0) {
    return <></>
  }
  return <>
    <Card.Title className='mb-1'>Realizacja</Card.Title>
    <Container as={Row} className='p-0 m-auto w-100'>
      <DetailsRow name='Zaakceptopwano' value={props.acceptedOrdered}/>
      <DetailsRow name='Zaakceptowane rozpoczęcie' value={props.acceptedStartDate}/>
      <DetailsRow name='Zrekrutowano' value={props.recruited}/>
    </Container>
  </>;
}

export default CommissionRecordDetails