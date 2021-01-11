import {Button, Modal} from "react-bootstrap";
import React from "react";

const CommissionDeleteModal = props => {
  const show = props.show
  const close = () => props.setShow(false)
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
      <Modal.Title>Potwierdzenie usunięcia</Modal.Title>
    </Modal.Header>
    <Modal.Body>
      Czy na pewno chcesz usunąć zamówienie: {props.commission}
    </Modal.Body>
    <Modal.Footer>
      <Button variant="secondary" onClick={close}>
        Anuluj
      </Button>
      <Button variant="primary" onClick={handleSubmit}>
        Usuń
      </Button>
    </Modal.Footer>
  </Modal>
}

export default CommissionDeleteModal