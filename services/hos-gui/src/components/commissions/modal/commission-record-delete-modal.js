import {Button, Modal} from "react-bootstrap";
import React from "react";

const CommissionRecordDeleteModal = props => {
  const showDeleteModal = props.recordToDelete != null
  const handleCloseDeleteModal = () => props.setRecordToDelete(null)
  const removeRecord = () => {
    props.handleRemoveRecord(props.recordToDelete[0])
    handleCloseDeleteModal()
  }

  return <Modal
      show={showDeleteModal}
      onHide={handleCloseDeleteModal}
      backdrop='static'
      keyboard={false}
  >
    <Modal.Header closeButton>
      <Modal.Title>Potwierdzenie usunięcia</Modal.Title>
    </Modal.Header>
    <Modal.Body>
      Czy na pewno chcesz usunąć
      rekord: {props.recordToDelete && (props.recordToDelete[1] || '#' + (props.recordToDelete[0] + 1))}
    </Modal.Body>
    <Modal.Footer>
      <Button variant="secondary" onClick={handleCloseDeleteModal}>
        Anuluj
      </Button>
      <Button variant="primary" onClick={removeRecord}>
        Usuń
      </Button>
    </Modal.Footer>
  </Modal>
}

export default CommissionRecordDeleteModal