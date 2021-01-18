import {Card, Container, ListGroup, Row} from "react-bootstrap";
import DetailsRow from "../../common/details-row";
import React from "react";

const CommissionDecisionDetails = props => {
  const commission = props.commission
  if (!commission || commission.status.id === 0 || commission.decisionDate == null) {
    return <></>
  }
  return <ListGroup.Item>
    <Card.Title className='mb-1'>Decyzja</Card.Title>
    <Container as={Row} className='p-0 m-auto w-100'>
      <DetailsRow name='Data decyzji' value={commission.decisionDate}/>
      <DetailsRow name='Data realizacji' value={commission.realisationDate}/>
      <DetailsRow name='Opiekun' value={commission.executor.email}/>
      <DetailsRow name='Uwagi' value={commission.decisionDescription}/>
    </Container>
  </ListGroup.Item>;
}

export default CommissionDecisionDetails