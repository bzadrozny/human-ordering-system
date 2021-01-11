import React from 'react'
import {Accordion, Card, Col, Container, Row} from "react-bootstrap";
import DetailsRow from "../../common/details-row";
import ShowHideToggle from "../../common/show-hide-toggle";

const CommissionRecordDetails = (props) => {
  const records = props.records

  return (
      <Card className='mt-3 mb-4 commission-details-card'>
        <Card.Header className='card-header-custom'><b>Zamawiane stanowiska</b></Card.Header>
        {records.map(record => (
                <Accordion key={record.id}>
                  <Card>
                    <Card.Header>
                      <Card.Title className='mb-1'>
                        Rekord: {record.id}
                      </Card.Title>
                      <Row className='mt-3' style={{fontSize: '1.2em'}}>
                        <Col xs={12} md={3} className='pt-xs-3'><b>Status:</b> {record.status.desc}</Col>
                        <Col xs={12} md={3} className='pt-xs-3'><b>Zamówiono:</b> {record.ordered}</Col>
                        <Col xs={12} md={5} className='pt-xs-3'><b>Stanowisko:</b> {record.jobName}</Col>
                        <Col sm={12} md={1} className='pt-xs-4'>
                          <ShowHideToggle eventKey={record.id}/>
                        </Col>
                      </Row>
                    </Card.Header>
                    <Accordion.Collapse eventKey={record.id}>
                      <Card.Body>
                        <DetailsRecordCard
                            record={record}
                        />
                        <DecisionRecordCard
                            record={record}
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

const DetailsRecordCard = props => {
  const record = props.record
  return <>
    <Card.Title className='mb-1'>Szczegóły</Card.Title>
    <Container as={Row} className='p-0 m-auto w-100'>
      <DetailsRow name='Pensja minimalna' value={record.wageRateMin} zł/>
      <DetailsRow name='Pensja maksymalna' value={record.wageRateMax} zł/>
      <DetailsRow name='Data rozpoczęcia' value={record.startDate}/>
      <DetailsRow name='Data zakończenia' value={record.endDate}/>
      <DetailsRow name='Rozliczenie' value={record.settlementType.desc}/>
      <DetailsRow name='Uwagi' value={record.description}/>
    </Container>
    <br/>
  </>
}

const DecisionRecordCard = props => {
  const record = props.record
  if (record.status.id === 0) {
    return <></>
  }
  return <>
    <Card.Title className='mb-1'>Decyzja: {record.status.desc}</Card.Title>
    <Container as={Row} className='p-0 m-auto w-100'>
      <DetailsRow name='Zaakceptopwano' value={record.acceptedOrdered}/>
      <DetailsRow name='Zaakceptowane rozpoczęcie' value={record.acceptedStartDate}/>
    </Container>
  </>;
}

export default CommissionRecordDetails