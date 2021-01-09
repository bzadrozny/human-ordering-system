import React, {useContext, useEffect, useState} from 'react'
import Toolbar from "../../board/board-toolbar";
import {CommissionAPI} from '../../../api/hos-service-api'
import {Card, ListGroup, Container, Row} from "react-bootstrap";
import DetailsRow from "../../common/details-row";
import CommissionRecordDetails from "./commissions-record-details";
import CommissionDeleteModal from "../modal/commission-delete-modal";
import CommissionSendModal from "../modal/commission-send-modal";
import CommissionAcceptModal from "../modal/commission-accept-modal";
import UserContext from "../../../context/user-context";
import CommissionRejectModal from "../modal/commission-reject-modal";

const CommissionsDetails = (props) => {
  const {id} = props.match.params;
  const [commission, setCommission] = useState(null)
  useEffect(() => {
    CommissionAPI.commissionsById(id)
        .then(resp => resp.data)
        .then(commission => setCommission(commission))
  }, [id])

  const user = useContext(UserContext)

  const editable = commission && commission.status.id in [0, 1]
  const removable = commission && commission.status.id in [0, 1]
  const sendable = commission
      && user && user.authorities.some(auth => auth.id === 4)
      && (commission.status.id === 0 || (
          commission.status.id === 1 && !commission.records.some(record => record.status.id === 3)
      ))
  const decision = commission
      && user && user.authorities.some(auth => auth.id in [0, 1, 2])
      && commission.status.id === 2
  const completable = commission
      && user && user.authorities.some(auth => auth.id in [0, 1, 2])
      && commission.status.id === 3
      && !commission.records.some(record => record.acceptedOrdered > record.recruited)

  const [showDeleteModal, setShowDeleteModal] = useState(false)
  const [showSendModal, setShowSendModal] = useState(false)
  const [showAcceptModal, setShowAcceptModal] = useState(false)
  const [showRejectModal, setShowRejectModal] = useState(false)
  const [showCancelModal, setShowCancelModal] = useState(false)

  return commission == null ? (
      <>Loading</>
  ) : (
      <>
        <Toolbar
            edit={editable ? () => props.history.push(props.location.pathname + '/edit') : null}
            remove={removable ? () => setShowDeleteModal(true) : null}
            send={sendable ? () => setShowSendModal(true) : null}
            accept={decision ? () => setShowAcceptModal(true) : null}
            reject={decision ? () => setShowRejectModal(true) : null}
            cancel={decision ? () => setShowCancelModal(true) : null} cancelLabel={'Odrzuć'}
            complete={completable ? () => console.log('zakończenie') : null}
            back={() => props.history.goBack()}
        />

        <CommissionDeleteModal
            commission={id}
            show={showDeleteModal}
            setShow={setShowDeleteModal}
            handleRemoveCommission={() =>
                CommissionAPI.delete(id).then(props.history.push({
                  pathname: '/board/commission',
                  state: {refresh: true}
                }))
            }
        />

        <CommissionSendModal
            commission={id}
            show={showSendModal}
            setShow={setShowSendModal}
            handleSendCommission={() => CommissionAPI.send(id)
                .then(resp => resp.data)
                .then(commission => setCommission(commission))
            }
        />

        <CommissionAcceptModal
            show={showAcceptModal}
            setShow={setShowAcceptModal}
            commission={commission}
            handleAcceptCommission={(decision) => CommissionAPI.decision(decision)
                .then(resp => resp.data)
                .then(commission => setCommission(commission))
            }
        />

        <CommissionRejectModal
            show={showRejectModal}
            setShow={setShowRejectModal}
            commission={commission}
            handleRejectCommission={(decision) => console.log(decision)}
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