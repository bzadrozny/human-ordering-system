import React from "react";
import {Modal} from "react-bootstrap";
import CommissionRecordForm from "../form/commission-record-form";
import CommissionRecordDecisionDetails from "../details/commission-record-decision-details";

const CommissionRecordFormModal = props => {
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

  const recordId = recordForm != null && (recordForm.id || ('#' + (props.recordForm[0] + 1)))
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
        {recordForm != null ? ("Edycja rekordu " + recordId) : "Nowy rekord"}
      </Modal.Title>
    </Modal.Header>
    <Modal.Body>
      <CommissionRecordDecisionDetails
          record={decision}
      />
      <CommissionRecordForm
          isAdmin={props.isAdmin}
          commissionId={props.commissionId}
          record={recordForm}
          saveRecord={saveRecord}
      />
    </Modal.Body>
  </Modal>
}

export default CommissionRecordFormModal