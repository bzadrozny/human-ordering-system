import {Card, Container, Row} from "react-bootstrap";
import DetailsRow from "../../common/details-row";
import React from "react";

const CommissionRecordContractDetails = props => {
  const contract = props.contract
  const candidate = {
    id: 1021,
    name: 'Andrzej',
    surname: 'Sołtys',
    email: 'andrzej.soltys@mail.com',
    phone: '999-888-777'
  }

  return <>
    <Card.Body>
      <Container as={Row} className='p-0 m-auto w-100'>
        <DetailsRow name='Data rozpoczęcia' value={contract.startDate}/>
        <DetailsRow name='Data zakończenia' value={contract.endDate}/>
        <DetailsRow name='Pensja' value={contract.salary + 'zł'}/>
        <DetailsRow name='Typ umowy' value={contract.contractType.desc}/>
      </Container>
      <Container as={Row} className='p-0 m-auto w-100'>
        <Card.Title className='mt-3'>
          Kandydat {candidate.id}
        </Card.Title>
        <Container as={Row} className='p-0 m-auto w-100'>
          <DetailsRow name='Imię' value={candidate.name}/>
          <DetailsRow name='Nazwisko' value={candidate.surname}/>
          <DetailsRow name='Email' value={candidate.email}/>
          <DetailsRow name='Telefon' value={candidate.phone}/>
        </Container>
      </Container>
    </Card.Body>
  </>
}

export default CommissionRecordContractDetails