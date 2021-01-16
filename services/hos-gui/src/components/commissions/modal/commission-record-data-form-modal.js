import React from "react";
import {Card, Modal} from "react-bootstrap";
import CommissionRecordDataForm from "../form/commission-record-data-form";
import DecisionRecordCard from "../details/decision-record-card";

const CommissionRecordDataFormModal = props => {
  const showModal = props.show
  const handleCloseModal = () => props.setShow(false)
  const recordForm = props.recordForm != null ? props.recordForm[1] : null
  const decision = props.decision
  const saveRecord = (newRecord) => {
    recordForm == null ?
        props.handleSaveRecord(newRecord) :
        props.handleSaveRecord(props.recordForm[0], newRecord)
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
        {recordForm != null ? ("Edycja rekordu " + recordForm.id) : "Nowy rekord"}
      </Modal.Title>
    </Modal.Header>
    <Modal.Body>
      <DecisionRecordCard
          record={decision}
      />
      <CommissionRecordDataForm
          isAdmin={props.isAdmin}
          commissionId={props.commissionId}
          record={recordForm}
          decision={decision}
          saveRecord={saveRecord}
      />
    </Modal.Body>
  </Modal>
}

export default CommissionRecordDataFormModal