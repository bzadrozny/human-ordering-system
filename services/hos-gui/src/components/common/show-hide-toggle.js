import React, {useContext} from "react";
import {AccordionContext, OverlayTrigger, Tooltip, useAccordionToggle} from "react-bootstrap";
import {RiArrowDownCircleLine, RiArrowUpCircleLine} from "react-icons/ri";

const ShowHideToggle = ({_, eventKey, callback}) => {
  const currentEventKey = useContext(AccordionContext);
  const accordionToggle = useAccordionToggle(eventKey, () => callback && callback(eventKey));
  const isCurrentEventKey = currentEventKey === eventKey;
  return (
      <OverlayTrigger
          placement='bottom'
          overlay={<Tooltip id="button-tooltip">{isCurrentEventKey ? 'Zwiń' : 'Rozwiń'}</Tooltip>}
      >
        <div className='commission-icon'>
          {isCurrentEventKey ?
              <RiArrowUpCircleLine onClick={accordionToggle}/> :
              <RiArrowDownCircleLine onClick={accordionToggle}/>
          }
        </div>
      </OverlayTrigger>
  )
}

export default ShowHideToggle