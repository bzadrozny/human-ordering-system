import React, {useState} from "react";
import {Button, Modal} from "react-bootstrap";
import {CommissionAPI, ContractAPI} from "../../../api/hos-service-api";
import ValidationErrors from "../../common/validation-errors";

const CommissionRecordContractDeleteModal = props => {
  const {
    show, setShow,
    contract,
    commission,
    setCommission,
    contractRecord
  } = {...props}

  const [success, setSuccess] = useState(false)
  const [restValidation, setRestValidation] = useState([])

  const close = () => {
    setSuccess(false)
    setShow(false)
  }

  const handleSubmit = () => {
    // ContractAPI.delete(commission.id, contractRecord.id, contract.id)
    //     .then(response => {
    //       const status = response.status
    //       const data = response.data
    //       if (status === 200 || status === 201) {
    //         handleSuccess(data)
    //       } else {
    //         handleExceptions(data)
    //       }
    //     })
  }

  const handleSuccess = async (response) => {
    setSuccess(true)
    await CommissionAPI.commissionsById(commission.id)
        .then(resp => resp.data)
        .then(commission => setCommission(commission))
    setTimeout(() => {
      close()
    }, 2000)
  }

  const handleExceptions = (response) => {
    setRestValidation(response.validationExceptions)
  }

  if (contract == null) return <></>
  return <Modal
      show={show}
      onHide={close}
      backdrop='static'
      keyboard={false}
  >
    <Modal.Header closeButton>
      <Modal.Title>Potwierdzenie usunięcia kontraktu</Modal.Title>
    </Modal.Header>
    <Modal.Body>
      <span>
        Czy na pewno chcesz usunąć zamówienie {contract.id}
      </span>
      <ValidationErrors
        validations={restValidation ? restValidation.map(ex => ex.message) : []}
      />
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

export default CommissionRecordContractDeleteModal