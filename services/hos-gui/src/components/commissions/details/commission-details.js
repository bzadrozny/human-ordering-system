import React, {useContext, useEffect, useState} from 'react'
import Toolbar from "../../board/board-toolbar";
import {CommissionAPI} from '../../../api/hos-service-api'
import {Card, ListGroup, Container, Row} from "react-bootstrap";
import DetailsRow from "../../common/details-row";
import CommissionRecordsDetails from "./commission-records-details";
import CommissionDeleteModal from "../modal/commission-delete-modal";
import CommissionSendModal from "../modal/commission-send-modal";
import CommissionAcceptModal from "../modal/commission-accept-modal";
import UserContext from "../../../context/user-context";
import CommissionRejectModal from "../modal/commission-reject-modal";
import CommissionCancelModal from "../modal/commission-cancel-modal";
import CommissionDecisionDetails from "./commission-decision-details";

const CommissionDetails = (props) => {
  const {id} = props.match.params;
  const [commission, setCommission] = useState(null)
  useEffect(() => {
    CommissionAPI.commissionsById(id)
        .then(resp => resp.data)
        .then(commission => setCommission(commission))
  }, [id])

  const user = useContext(UserContext)

  const editable = commission
      && [-1, 0, 1].some(id => id === commission.status.id)
      && user && (user.authorities.some(auth => auth.id === 0) || user.id === commission.principal.id)
  const sendable = commission
      && user && user.authorities.some(auth => auth.id === 4)
      && (commission.status.id === 0 || (
          commission.status.id === 1
          && !commission.records.some(record => record.status.id === 3)
          && commission.records.some(record => record.status.id in [0, 1, 2])
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
            editLabel={editable && [-1, 1].some(id => id === commission.status.id) && 'Popraw'}
            remove={editable ? () => setShowDeleteModal(true) : null}
            removeLabel={editable && [-1, 1].some(id => id === commission.status.id) && 'Odrzuć'}
            send={sendable ? () => setShowSendModal(true) : null}
            accept={decision ? () => setShowAcceptModal(true) : null}
            reject={decision ? () => setShowRejectModal(true) : null}
            cancel={decision ? () => setShowCancelModal(true) : null}
            cancelLabel={'Odrzuć'}
            complete={completable ? () => console.log('zakończenie') : null}
            back={() => props.history.goBack()}
        />

        {editable && <CommissionDeleteModal
            commission={id}
            show={showDeleteModal}
            setShow={setShowDeleteModal}
            commissionStatus={commission.status.id}
            handleRemoveCommission={
              () => CommissionAPI.delete(id).then(props.history.push({
                pathname: '/board/commission',
                state: {refresh: true}
              }))
            }
        />}

        {sendable && <CommissionSendModal
            commission={id}
            show={showSendModal}
            setShow={setShowSendModal}
            handleSendCommission={() => CommissionAPI.send(id)
                .then(resp => resp.data)
                .then(commission => setCommission(commission))
            }
        />}

        {decision && <>
          <CommissionAcceptModal
              show={showAcceptModal}
              setShow={setShowAcceptModal}
              commission={commission}
              setCommission={(commission) => setCommission(commission)}
          />
          <CommissionRejectModal
              show={showRejectModal}
              setShow={setShowRejectModal}
              commission={commission}
              setCommission={(commission) => setCommission(commission)}
          />
          <CommissionCancelModal
              show={showCancelModal}
              setShow={setShowCancelModal}
              commission={commission}
              setCommission={(commission) => setCommission(commission)}
          />
        </>}
        <Container>
          <DetailsCard
              commission={commission}
          />
          <CommissionRecordsDetails
              commission={commission}
              setCommission={(commission) => setCommission(commission)}
          />
        </Container>
      </>
  )
}

const DetailsCard = props => {
  const commission = props.commission
  return <Card className='commission-details-card'>
    <Card.Header className='card-header-custom'><b>Zlecenie {commission.id}</b></Card.Header>
    <ListGroup variant="flush">
      <CommissionDetailsCard commission={commission}/>
      <CommissionDecisionDetails commission={commission}/>
    </ListGroup>
  </Card>
}

const CommissionDetailsCard = props => {
  const commission = props.commission
  return <ListGroup.Item>
    <Card.Title className='mb-1'>Szczegóły</Card.Title>
    <Container as={Row} className='p-0 m-auto w-100'>
      <DetailsRow name='Status' value={commission.status.desc}/>
      <DetailsRow name='Zlecający' value={commission.principal && commission.principal.email}/>
      <DetailsRow name='Data zamówienia' value={commission.orderDate}/>
      <DetailsRow name='Lokalizacja' value={commission.location.name}/>
      <DetailsRow name='Opis' value={commission.description}/>
    </Container>
  </ListGroup.Item>

}


export default CommissionDetails