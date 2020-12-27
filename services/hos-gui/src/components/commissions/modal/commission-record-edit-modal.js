import React from "react";
import {Modal} from "react-bootstrap";
import CommissionRecordDataForm from "../form/commission-record-data-form";

const CommissionRecordEditModal = props => {
  const showDeleteModal = props.recordToEdit != null
  const handleCloseDeleteModal = () => props.setRecordToEdit(null)
  const editRecord = (newRecord) => {
    props.handleEditRecord(props.recordToEdit[0], newRecord)
    handleCloseDeleteModal()
  }

  return <Modal
      show={showDeleteModal}
      onHide={handleCloseDeleteModal}
      size='lg'
      backdrop='static'
      keyboard={false}
      className='pr-0'
  >
    <Modal.Header closeButton>
      <Modal.Title>Edycja rekordu {}</Modal.Title>
    </Modal.Header>
    <Modal.Body>
      <CommissionRecordDataForm
          isAdmin={props.isAdmin}
          commission={props.recordToEdit && props.recordToEdit[1].commission}
          record={props.recordToEdit && props.recordToEdit[1]}
          editRecord={editRecord}
      />
    </Modal.Body>
  </Modal>
}

export default CommissionRecordEditModal