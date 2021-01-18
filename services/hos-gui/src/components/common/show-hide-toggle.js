import React, {useContext} from "react";
import {AccordionContext, OverlayTrigger, Tooltip, useAccordionToggle} from "react-bootstrap";
import {RiArrowDownCircleLine, RiArrowUpCircleLine} from "react-icons/ri";

const ShowHideToggle = ({_, eventKey, className, fontSize, callback}) => {
  const currentEventKey = useContext(AccordionContext);
  const accordionToggle = useAccordionToggle(eventKey, () => callback && callback(eventKey));
  const isCurrentEventKey = currentEventKey === eventKey;
  const theme = className || 'commission-icon'
  return (
      <OverlayTrigger
          placement='bottom'
          overlay={<Tooltip id="button-tooltip">{isCurrentEventKey ? 'Zwiń' : 'Rozwiń'}</Tooltip>}
      >
        <div className={theme} style={{fontSize}}>
          {isCurrentEventKey ?
              <RiArrowUpCircleLine onClick={accordionToggle} fontSize={fontSize}/> :
              <RiArrowDownCircleLine onClick={accordionToggle} fontSize={fontSize}/>
          }
        </div>
      </OverlayTrigger>
  )
}

export default ShowHideToggle