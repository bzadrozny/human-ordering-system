import {Button, Modal} from "react-bootstrap";
import React from "react";

const CommissionSendModal = props => {
  const show = props.show
  const close = () => props.setShow(false)
  const sendCommission = () => {
    props.handleSendCommission()
    close()
  }

  return <Modal
      show={show}
      onHide={close}
      backdrop='static'
      keyboard={false}
  >
    <Modal.Header closeButton>
      <Modal.Title>Potwierdzenie wysłania zamówienia </Modal.Title>
    </Modal.Header>
    <Modal.Body>
      Czy na pewno chcesz wysłać zamówienie: {props.commission}
    </Modal.Body>
    <Modal.Footer>
      <Button variant="secondary" onClick={close}>
        Anuluj
      </Button>
      <Button variant="primary" onClick={sendCommission}>
        Wyślij
      </Button>
    </Modal.Footer>
  </Modal>
}

export default CommissionSendModal