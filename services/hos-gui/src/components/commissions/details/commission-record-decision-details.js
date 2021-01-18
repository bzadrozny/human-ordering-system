import {Card, Container, Row} from "react-bootstrap";
import DetailsRow from "../../common/details-row";
import React from "react";

const CommissionRecordDecisionDetails = props => {
  const record = props.record
  if (!record || record.status.id === 0) {
    return <></>
  }
  return <>
    <Card.Header className='card-header-custom'>
      <Card.Title className='mb-1' style={{fontSize: '1em'}}>
        Decyzja: {record.status.desc}
      </Card.Title>
    </Card.Header>
    <Card.Body className='px-0'>
      <Container as={Row} className='p-0 m-auto w-100'>
        <DetailsRow name='Akceptopwano' value={record.acceptedOrdered}/>
        <DetailsRow name='Praca od' value={record.acceptedStartDate}/>
      </Container>
    </Card.Body>
  </>
}

export default CommissionRecordDecisionDetails