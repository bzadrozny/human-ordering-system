import {Card, Container, Row} from "react-bootstrap";
import DetailsRow from "../../common/details-row";
import React from "react";

const DecisionRecordCard = props => {
  const record = props.record
  if (!record || record.status.id === 0) {
    return <></>
  }
  return <Card>
    <Card.Header>
      <Card.Title className='mb-0'>Decyzja: {record.status.desc}</Card.Title>
    </Card.Header>
    <Card.Body className='py-2'>
      <Container as={Row} className='p-0 m-auto w-100'>
        <DetailsRow lgTitle={12} lgValue={12} name='Zaakceptopwano' value={record.acceptedOrdered}/>
        <DetailsRow lgTitle={12} lgValue={12} name='Zaakceptowane rozpoczęcie' value={record.acceptedStartDate}/>
      </Container>
    </Card.Body>
  </Card>
}

export default DecisionRecordCard