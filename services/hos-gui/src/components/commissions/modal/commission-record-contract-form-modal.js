import React, {useState} from "react";
import {Modal} from "react-bootstrap";
import CommissionContractForm from "../form/commission-contract-form";
import {CommissionAPI, ContractAPI} from "../../../api/hos-service-api";
import {createOrEdit} from "../../../services/utils/axios-utils";

const CommissionRecordContractFormModal = props => {
  const {
    show,
    setShow,
    contract,
    commission,
    contractRecord,
    setCommission
  } = {...props}

  const [restValidation, setRestValidation] = useState([])
  const [success, setSuccess] = useState(false)
  const [contractId, setContractId] = useState(null)

  const handleSubmit = async (contractForm) => {
    createOrEdit(contractForm, () => ContractAPI.create(contractForm), () => ContractAPI.edit(contractForm))
        .then(response => {
          const status = response.status
          const data = response.data
          if (status === 200 || status === 201) {
            handleSuccess(data)
          } else {
            handleExceptions(data)
          }
        })
  }

  const handleSuccess = async (response) => {
    setSuccess(true)
    setContractId(response.id)
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

  const close = () => {
    setSuccess(false)
    setContractId(null)
    setShow(false)
  }

  return <Modal
      show={show}
      onHide={close}
      size='lg'
      backdrop='static'
      keyboard={false}
      className='pr-0'
  >
    <Modal.Header closeButton>
      <Modal.Title>
        {contract ? ("Edycja kontraktu " + contract.id) : "Nowy kontrakt"}
      </Modal.Title>
    </Modal.Header>
    <Modal.Body>
      {success ? (
          'Zapisano kontrakt: ' + contractId
      ) : (
          <CommissionContractForm
              contract={contract}
              contractRecord={contractRecord}
              commissionId={commission.id}
              handleSubmit={handleSubmit}
              restValidation={restValidation}
          />
      )}
    </Modal.Body>
  </Modal>
}

export default CommissionRecordContractFormModal