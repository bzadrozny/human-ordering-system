import React from "react";
import {Modal} from "react-bootstrap";
import CommissionRecordDataForm from "../form/commission-record-data-form";

const CommissionRecordDataFormModal = props => {
  const showModal = props.show
  const handleCloseModal = () => props.setShow(false)
  const record = props.record != null ? props.record[1] : null
  const saveRecord = (newRecord) => {
    record == null ?
        props.handleSaveRecord(newRecord) :
        props.handleSaveRecord(props.record[0], newRecord)
    handleCloseModal()
  }

  return <Modal
      show={showModal}
      onHide={handleCloseModal}
      size='lg'
      backdrop='static'
      keyboard={false}
      className='pr-0'
  >
    <Modal.Header closeButton>
      <Modal.Title>
        {props.record != null ? ("Edycja rekordu " + props.record[1].id) : "Nowy rekord"}
      </Modal.Title>
    </Modal.Header>
    <Modal.Body>
      <CommissionRecordDataForm
          isAdmin={props.isAdmin}
          commission={props.commission}
          record={record}
          saveRecord={saveRecord}
      />
    </Modal.Body>
  </Modal>
}

export default CommissionRecordDataFormModal