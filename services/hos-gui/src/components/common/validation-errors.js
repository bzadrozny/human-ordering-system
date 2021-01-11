import React, {useState} from "react";
import {Alert} from "react-bootstrap";

const ValidationErrors = props => {
  return <>
    {props.validations.map((validation, idx) => <ValidationError key={idx} validation={validation}/>)}
  </>
}

const ValidationError = props => {
  const [show, setShow] = useState(true)
  return (
      <Alert
          variant="danger"
          show={show}
          onClose={() => setShow(false)}
          className='mt-3'
          dismissible
      >
        <p>{props.validation}</p>
      </Alert>
  )
}

export default ValidationErrors