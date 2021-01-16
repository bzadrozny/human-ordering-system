import {Button, Modal} from "react-bootstrap";
import React from "react";

const CommissionDeleteModal = props => {
  const show = props.show
  const close = () => props.setShow(false)
  const commissionStatus = props.commissionStatus
  const handleSubmit = () => {
    props.handleRemoveCommission()
    close()
  }

  return <Modal
      show={show}
      onHide={close}
      backdrop='static'
      keyboard={false}
  >
    <Modal.Header closeButton>
      <Modal.Title>Potwierdzenie {commissionStatus === -1 ? 'rezygnacji': 'usunięcia'}</Modal.Title>
    </Modal.Header>
    <Modal.Body>
      Czy na pewno chcesz {commissionStatus === -1 ? 'zrezygnować z zamówienia': 'usunąć zamówienie'}: {props.commission}
    </Modal.Body>
    <Modal.Footer>
      <Button variant="secondary" onClick={close}>
        Anuluj
      </Button>
      <Button variant="primary" onClick={handleSubmit}>
        {commissionStatus === -1 ? 'Zrezygnuj': 'Usuń'}
      </Button>
    </Modal.Footer>
  </Modal>
}

export default CommissionDeleteModal