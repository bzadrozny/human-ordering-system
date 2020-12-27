import React, {useEffect, useState} from 'react'
import Toolbar from "../../board/board-toolbar";
import {CommissionAPI} from '../../../api/hos-service-api'
import {Card, ListGroup, Container, Row} from "react-bootstrap";
import DetailsRow from "../../common/details-row";
import CommissionRecordDetails from "./commissions-record-details";
import CommissionDeleteModal from "../modal/commission-delete-modal";

const CommissionsDetails = (props) => {
  const {id} = props.match.params;
  const [commission, setCommission] = useState(null)
  useEffect(() => {
    CommissionAPI.commissionsById(id)
        .then(resp => resp.data)
        .then(commission => setCommission(commission))
  }, [id])

  const editable = commission && commission.status.id in [0, 1]
  const removable = commission && commission.status.id in [0, 1]
  const sendable = commission && commission.status.id in [0, 1]
  const acceptable = true
  const rejectable = true
  const completable = true

  const [showCommissionDeleteModal, setShowCommissionDeleteModal] = useState(false)

  return commission === null ? (
      <>Loading</>
  ) : (
      <>
        <Toolbar
            edit={editable ? () => props.history.push(props.location.pathname + '/edit') : null}
            remove={removable ? () => setShowCommissionDeleteModal(true) : null}
            send={sendable ? () => console.log('wyslanie') : null}
            accept={acceptable ? () => console.log('akceptacja') : null}
            reject={rejectable ? () => console.log('zastrzeżenia') : null}
            complete={completable ? () => console.log('zakończenie') : null}
            back={() => props.history.goBack()}
        />

        <CommissionDeleteModal
            commission={id}
            showCommissionDeleteModal={showCommissionDeleteModal}
            setShowCommissionDeleteModal={setShowCommissionDeleteModal}
            handleRemoveCommission={() =>
                CommissionAPI.delete(id).then(props.history.push({
                  pathname: '/board/commission',
                  state: {deletedCommission: id}
                }))
            }
        />

        <Container>
          <Card className='commission-details-card'>
            <Card.Header className='card-header-custom'><b>Zlecenie {commission.id}</b></Card.Header>
            <ListGroup variant="flush">
              <ListGroup.Item>
                <Card.Title className='mb-1'>Stan zamówienia</Card.Title>
                <Container as={Row} className='p-0 m-auto w-100'>
                  <DetailsRow name='Status' value={commission.status.desc}/>
                  <DetailsRow name='Data zamówienia' value={commission.orderDate}/>
                  <DetailsRow name='Data zatwierdzenia' value={commission.decisionDate}/>
                  <DetailsRow name='Data zakończenia' value={commission.completedDate}/>
                </Container>
              </ListGroup.Item>
              <ListGroup.Item>
                <Card.Title className='mb-1'>Szczegóły</Card.Title>
                <Container as={Row} className='p-0 m-auto w-100'>
                  <DetailsRow name='Zlecający' value={commission.principal && commission.principal.email}/>
                  <DetailsRow name='Lokalizacja' value={commission.location.name}/>
                  <DetailsRow name='Opiekun zamówienia' value={commission.executor && commission.executor.email}/>
                  <DetailsRow name='Opis' value={commission.description}/>
                </Container>
              </ListGroup.Item>
            </ListGroup>
          </Card>
          <CommissionRecordDetails records={commission.records} status={commission.status}/>
        </Container>

      </>
  )
}

export default CommissionsDetails