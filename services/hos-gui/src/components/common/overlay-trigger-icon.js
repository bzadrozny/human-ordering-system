import React from "react";
import {OverlayTrigger, Tooltip} from "react-bootstrap";
import '../commissions/style/commissions.css'

const OverlayTriggerIcon = props => {
  const placement = props.placement || 'bottom'
  const overlay = props.overlay
  const icon = props.icon
  return (
      <OverlayTrigger
          placement={placement}
          overlay={
            <Tooltip id="button-tooltip">{overlay}</Tooltip>
          }
      >
        <div className='commission-icon'>
          {icon}
        </div>
      </OverlayTrigger>
  )

}

export default OverlayTriggerIcon