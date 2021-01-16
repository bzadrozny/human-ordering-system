import {Card, Container, Row} from "react-bootstrap";
import DetailsRow from "../../common/details-row";
import React from "react";

const DetailsRecordCard = props => {
  const record = props.record
  return <Card.Body className='px-0'>
    <Card.Title className='mb-1'>Szczegóły</Card.Title>
    <Container as={Row} className='p-0 m-auto w-100'>
      <DetailsRow name='Pensja minimalna' value={record.wageRateMin} zł/>
      <DetailsRow name='Pensja maksymalna' value={record.wageRateMax} zł/>
      <DetailsRow name='Data rozpoczęcia' value={record.startDate}/>
      <DetailsRow name='Data zakończenia' value={record.endDate}/>
      <DetailsRow name='Rozliczenie' value={record.settlementType.desc}/>
      <DetailsRow name='Uwagi' value={record.description}/>
    </Container>
  </Card.Body>
}

export default DetailsRecordCard