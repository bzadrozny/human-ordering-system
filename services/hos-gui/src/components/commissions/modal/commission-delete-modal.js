import {Button, Modal} from "react-bootstrap";
import React from "react";

const CommissionDeleteModal = props => {
  const showCommissionDeleteModal = props.showCommissionDeleteModal
  const handleCloseDeleteModal = () => props.setShowCommissionDeleteModal(false)
  const removeCommission = () => {
    props.handleRemoveCommission()
    handleCloseDeleteModal()
  }

  return <Modal
      show={showCommissionDeleteModal}
      onHide={handleCloseDeleteModal}
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
      <Button variant="secondary" onClick={handleCloseDeleteModal}>
        Anuluj
      </Button>
      <Button variant="primary" onClick={removeCommission}>
        Usuń
      </Button>
    </Modal.Footer>
  </Modal>
}

export default CommissionDeleteModal