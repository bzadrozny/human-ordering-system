import {Button, Modal} from "react-bootstrap";
import React from "react";

const CommissionRejectModal = props => {
  const show = props.show
  const close = () => props.setShow(false)
  const rejectCommission = () => {
    props.handleRejectCommission('test')
    close()
  }

  return <Modal
      show={show}
      onHide={close}
      backdrop='static'
      keyboard={false}
  >
    <Modal.Header closeButton>
      <Modal.Title>Zastrzeż zamówienie</Modal.Title>
    </Modal.Header>
    <Modal.Body>
      ZASTRZEŻENIE ZAMÓWIENIA
    </Modal.Body>
    <Modal.Footer>
      <Button variant="secondary" onClick={close}>
        Anuluj
      </Button>
      <Button variant="primary" onClick={rejectCommission}>
        Zastrzeż
      </Button>
    </Modal.Footer>
  </Modal>
}

export default CommissionRejectModal