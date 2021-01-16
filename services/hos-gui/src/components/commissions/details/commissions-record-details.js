import React from 'react'
import {Accordion, Card, Col, Container, Row} from "react-bootstrap";
import ShowHideToggle from "../../common/show-hide-toggle";
import DecisionRecordCard from "./decision-record-card";
import DetailsRecordCard from "./commission-record-details";

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
                        <DecisionRecordCard
                            record={record}
                        />
                        <DetailsRecordCard
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

export default CommissionRecordDetails