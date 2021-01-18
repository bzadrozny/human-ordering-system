import {Col, Row} from "react-bootstrap";
import React from "react";

const DetailsRow = (props) => {
  const oneRow = props.oneRow
  const smName = props.smTitle != null ? props.smTitle : 5
  const smValue = props.smValue != null ? props.smValue : 7
  const lgName = props.lgTitle != null ? props.lgTitle : 4
  const lgValue = props.lgValue != null ? props.lgValue : 8
  return (
      <Col xs={12} lg={oneRow ? 12 : 6} className='ml-1 p-2 border-bottom' as={Row} >
        <Col xs={12} sm={smName} lg={lgName} className='my-auto'>
          <b>{props.name}: </b>
        </Col>
        <Col xs={12} sm={smValue} lg={lgValue} className='my-auto'>
          {props.value || 'BRAK'}
        </Col>
      </Col>
  )
}

export default DetailsRow