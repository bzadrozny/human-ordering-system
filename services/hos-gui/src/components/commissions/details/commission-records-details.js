import React, {useContext, useState} from 'react'
import {Accordion, Card, Col, Row} from "react-bootstrap";
import ShowHideToggle from "../../common/show-hide-toggle";
import CommissionRecordDecisionDetails from "./commission-record-decision-details";
import CommissionRecordDetails from "./commission-record-details";
import CommissionContractsDetails from "./commission-record-contracts-details";
import CommissionRecordContractFormModal from "../modal/commission-record-contract-form-modal";
import UserContext from "../../../context/user-context";
import CommissionRecordContractDeleteModal from "../modal/commission-record-contract-delete-modal";

const CommissionRecordsDetails = (props) => {
  const user = useContext(UserContext)
  const {
    commission,
    setCommission
  } = {...props}

  const canModifyContract = commission
      && user && user.authorities.some(auth => auth.id === 0 || auth.id === 3)
      && commission.status.id === 3
  const [contract, setContract] = useState(null)
  const [contractRecord, setContractRecord] = useState(null)
  const [showContractFormModal, setShowContractFormModal] = useState(false)
  const [showDeleteContractModal, setShowDeleteContractModal] = useState(false)

  return (
      <Card className='mt-3 mb-4 commission-details-card'>

        {showContractFormModal && <CommissionRecordContractFormModal
            show={showContractFormModal}
            setShow={setShowContractFormModal}
            contract={contract}
            commission={commission}
            contractRecord={contractRecord}
            setCommission={(commission) => setCommission(commission)}
        />}

        {showDeleteContractModal && <CommissionRecordContractDeleteModal
            show={showDeleteContractModal}
            setShow={setShowDeleteContractModal}
            contract={contract}
            commission={commission}
            contractRecord={contractRecord}
            setCommission={(commission) => setCommission(commission)}
        />}

        <Card.Header className='card-header-custom'><b>Zamawiane stanowiska</b></Card.Header>
        {commission.records
            .sort((a, b) => a.id - b.id)
            .map(record => (
                <Accordion key={record.id}>
                  <Card.Header>
                    <Card.Title className='mb-1'>
                      Rekord {record.id}
                    </Card.Title>
                    <Row className='mt-3' style={{fontSize: '1.2em'}}>
                      <Col xs={12} md={3} className='pt-xs-3'><b>Status:</b> {record.status.desc}</Col>
                      <Col xs={12} md={3} className='pt-xs-3'>
                        {(record.status.id === 1 || record.status.id === 5) ? <>
                          <b>Zrealizowano:</b> {record.recruited}/{record.ordered}
                        </> : <>
                          <b>Zamówiono:</b> {record.ordered}
                        </>}
                      </Col>
                      <Col xs={12} md={5} className='pt-xs-3'><b>Stanowisko:</b> {record.jobName}</Col>
                      <Col sm={12} md={1} className='pt-xs-4'><ShowHideToggle eventKey={record.id}/></Col>
                    </Row>
                  </Card.Header>
                  <Accordion.Collapse eventKey={record.id}>
                    <Card.Body>
                      <CommissionRecordDetails
                          record={record}
                      />
                      <CommissionRecordDecisionDetails
                          record={record}
                      />
                      <CommissionContractsDetails
                          record={record}
                          commissionStatus={commission.status}
                          canModifyContract={canModifyContract}
                          setContract={setContract}
                          setContractRecord={setContractRecord}
                          setShowContractFormModal={setShowContractFormModal}
                          setShowDeleteContractModal={setShowDeleteContractModal}
                      />
                    </Card.Body>
                  </Accordion.Collapse>
                </Accordion>
            ))}
      </Card>
  )
}

export default CommissionRecordsDetails