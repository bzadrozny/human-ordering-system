import React from 'react';
import {
  RiAddLine,
  RiEditLine,
  RiSendPlaneFill,
  RiThumbUpLine,
  RiThumbDownLine,
  RiCloseFill,
  RiArrowGoBackLine,
  RiDeleteBinLine,
  RiLockLine,
} from "react-icons/ri";
import {OverlayTrigger, Tooltip} from "react-bootstrap";

const Toolbar = (props) => {

  const create = props.create && (
      <ToolbarIcon icon={RiAddLine} label="Utwórz" onClick={props.create}/>
  )

  const edit = props.edit && (
      <ToolbarIcon icon={RiEditLine} label="Edytuj" onClick={props.edit}/>
  )

  const remove = props.remove && (
      <ToolbarIcon icon={RiDeleteBinLine} label="Usuń" onClick={props.remove}/>
  )

  const sent = props.send && (
      <ToolbarIcon icon={RiSendPlaneFill} label="Wyślij" onClick={props.send}/>
  )

  const accept = props.accept && (
      <ToolbarIcon icon={RiThumbUpLine} label="Zaakceptuj" onClick={props.accept}/>
  )

  const reject = props.reject && (
      <ToolbarIcon icon={RiThumbDownLine} label="Zastrzeż" onClick={props.reject}/>
  )

  const complete = props.complete && (
      <ToolbarIcon icon={RiLockLine} label="Zakończ" onClick={props.complete}/>
  )

  const cancel = props.cancel && (
      <ToolbarIcon icon={RiCloseFill} label={props.cancelLabel || "Anuluj"} onClick={props.cancel}/>
  )

  const back = props.back && (
      <ToolbarIcon icon={RiArrowGoBackLine} label="Wróć" onClick={props.back} ToolbarIcon/>
  )

  return (
      <div className='toolbar mx-auto align-content-center'>
        {create}
        {edit}
        {remove}
        {sent}
        {accept}
        {reject}
        {complete}
        {cancel}
        {back}
      </div>
  )
}

const ToolbarIcon = (props) => {
  return (
      <OverlayTrigger placement='right' overlay={<Tooltip id="button-tooltip">{props.label}</Tooltip>}>
        <div className='toolbar-item' onClick={props.onClick}>
          {props.icon({size: 21})}
        </div>
      </OverlayTrigger>
  )
}

export default Toolbar