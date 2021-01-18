import React from "react";
import {OverlayTrigger, Tooltip} from "react-bootstrap";
import '../commissions/style/commissions.css'

const OverlayTriggerIcon = props => {
  const placement = props.placement || 'bottom'
  const overlay = props.overlay
  const icon = props.icon
  const theme = props.theme || 'commission-icon'
  const fontSize = props.fontSize
  return (
      <OverlayTrigger
          placement={placement}
          overlay={
            <Tooltip id="button-tooltip">{overlay}</Tooltip>
          }
      >
        <div className={theme} style={{fontSize}}>
          {icon}
        </div>
      </OverlayTrigger>
  )

}

export default OverlayTriggerIcon