import {Col, Row} from "react-bootstrap";
import React from "react";

const DetailsRow = (props) => {
  return (
      <Col xs={12} lg={6} className='ml-1 p-2 border-bottom' as={Row} >
        <Col xs={12} sm={5} lg={4} className='my-auto'>
          <b>{props.name}: </b>
        </Col>
        <Col xs={12} sm={7} lg={8} className='my-auto'>
          {props.value || 'BRAK'}
        </Col>
      </Col>
  )
}

export default DetailsRow