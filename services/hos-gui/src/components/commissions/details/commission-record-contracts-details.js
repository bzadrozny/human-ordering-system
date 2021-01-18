import {Accordion, Card, Col, Row} from "react-bootstrap";
import React, {useContext, useState} from "react";
import OverlayTriggerIcon from "../../common/overlay-trigger-icon";
import {RiAddLine, RiDeleteBinLine, RiEditLine, RiThumbDownLine, RiThumbUpLine} from "react-icons/ri";
import ShowHideToggle from "../../common/show-hide-toggle";
import CommissionRecordContractDetails from "./commission-record-contract-details";
import UserContext from "../../../context/user-context";

const CommissionContractsDetails = props => {
  const user = useContext(UserContext)
  const {
    record,
    commissionStatus,
    canModifyContract,
    setContract,
    setContractRecord,
    setShowContractFormModal,
    setShowDeleteContractModal
  } = {...props}

  const [acceptance, setAcceptance] = useState([1000, 1001, 1002, 1003, 1004])

  if (!commissionStatus || commissionStatus.id !== 3 || !record || (record.status.id !== 1 && record.status.id !== 5)) {
    return <></>
  }

  const addNewContract = () => {
    setContract(null)
    setContractRecord(record)
    setShowContractFormModal(true)
  }

  const editContract = (contract) => {
    setContract(contract)
    setContractRecord(record)
    setShowContractFormModal(true)
  }

  const deleteContract = (contract) => {
    setContract(contract)
    setShowDeleteContractModal(true)
  }

  const isClient = user && user.authorities.some(auth => auth.id === 4)
  return <Accordion key={'r' + record.id}>
    <Card>
      <Card.Header className='card-header-custom'>
        <Card.Title className='mb-1' style={{fontSize: '1em'}}>
          Realizacja: {record.recruited} / {record.acceptedOrdered}
          {record.recruited !== 0 && <ShowHideToggle
              eventKey={record.id}
              className='commission-icon-white'
              fontSize={21}
          />}
          {canModifyContract && record.recruited !== record.acceptedOrdered && <OverlayTriggerIcon
              overlay='Dodaj kontrakt'
              theme='commission-icon-white'
              fontSize={21}
              icon={<RiAddLine onClick={addNewContract} fontSize={21}/>}
          />}
        </Card.Title>
      </Card.Header>

      <Accordion.Collapse eventKey={record.id}>
        <>{record.contracts
            .sort((a, b) => a.id - b.id)
            .map(contract => (
                <Accordion key={contract.code}>
                  <Card.Header>
                    <Card.Title>
                      Kontrakt {contract.id}
                    </Card.Title>
                    <Row>
                      <Col xs={12} md={3} className='pt-xs-3'><b>Akceptacja:</b> {acceptance.some(acp => acp === contract.id) ?
                          <RiThumbUpLine fontSize={21} style={{color: '#28a745'}}/> :
                          <RiThumbDownLine fontSize={21} style={{color: '#dc3545'}}/>}
                      </Col>
                      <Col xs={12} md={3} className='pt-xs-3'><b>Data umowy:</b> {contract.contractDate}</Col>
                      <Col xs={12} md={3} className='pt-xs-3'><b>Numer umowy:</b> {contract.code}</Col>
                      <Col sm={12} md={3} className='pt-xs-3'>
                        <ShowHideToggle
                            eventKey={contract.code}
                            fontSize={21}
                        />
                        {canModifyContract && !acceptance.some(acp => acp === contract.id) && <>
                          <OverlayTriggerIcon
                              overlay='Usuń'
                              fontSize={21}
                              icon={<RiDeleteBinLine onClick={() => deleteContract(contract)} fontSize={21}/>}
                          />
                          <OverlayTriggerIcon
                              overlay='Edytuj'
                              fontSize={21}
                              icon={<RiEditLine onClick={() => editContract(contract)} fontSize={21}/>}
                          />
                        </>
                        }
                        {isClient && <>
                          <OverlayTriggerIcon
                              overlay='Odrzuć'
                              fontSize={21}
                              icon={
                                <RiThumbDownLine
                                    onClick={() => {
                                      const acceptance_ = [...acceptance]
                                      acceptance_[contract.id] = false
                                      setAcceptance(acceptance_)
                                    }}
                                    className='danger-on-hover'
                                    fontSize={21}
                                />
                              }
                          />
                          <OverlayTriggerIcon
                              overlay='Zaakceptuj'
                              fontSize={21}
                              icon={
                                <RiThumbUpLine
                                    onClick={() => {
                                      const acceptance_ = [...acceptance]
                                      acceptance_[contract.id] = true
                                      setAcceptance(acceptance_)
                                    }}
                                    fontSize={21}
                                />
                              }
                          />
                        </>}
                      </Col>
                    </Row>
                  </Card.Header>
                  <Accordion.Collapse eventKey={contract.code}>
                    <CommissionRecordContractDetails
                        contract={contract}
                    />
                  </Accordion.Collapse>
                </Accordion>
            ))}</>
      </Accordion.Collapse>
    </Card>
  </Accordion>
}

export default CommissionContractsDetails