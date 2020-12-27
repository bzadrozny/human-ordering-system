import React, {useContext} from "react";
import {Nav, Navbar, Dropdown, NavDropdown, OverlayTrigger, Tooltip} from "react-bootstrap";
import {NavLink} from "react-router-dom";
import {RiBriefcaseLine, RiBarChartFill, RiGovernmentLine, RiUserSettingsLine} from "react-icons/ri"
import logo from "../../resources/hos-200x75.png";
import AuthService from "../../services/authentication/auth-service";
import UserContext from "../../context/user-context";

let Header = () => {
  const user = useContext(UserContext)
  return (
      <Navbar expand="md" bg="dark" variant="dark">
        <Navbar.Brand href="/board/commission">
          <img
              alt="HOS"
              src={logo}
              width="125"
              height="50"
              className="d-inline-block align-top"
          />{' '}
        </Navbar.Brand>
        <Navbar.Toggle aria-controls='navbar-collapsed'/>
        <Navbar.Collapse id='navbar-collapsed' className="justify-content-end">
          <Nav>
            <NavLinkIcon link='commission' label='Zamówienia' icon={<RiBriefcaseLine size={21}/>}/>
            <NavLinkIcon link='statistic' label='Statystyka' icon={<RiBarChartFill size={21}/>}/>
            <NavLinkIcon link='administration' label='Administracja' icon={<RiGovernmentLine size={21}/>}/>
            <NavLinkIcon link='setting' label='Ustawienia' icon={<RiUserSettingsLine size={21}/>}/>
            <NavDropdown
                id='basic-nav-dropdown'
                className='mr-lg-5 col-md-5'
                title={user ? user.email : 'email'}
            >
              <Dropdown.Item onClick={AuthService.logout} className='text-center'>
                Wyloguj
              </Dropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
  )
}

let NavLinkIcon = (props) => {
  let eventKey = 'l-' + props.link
  let linkTo = '/board/' + props.link
  return (
      <Nav.Link eventKey={eventKey} as={NavLink} to={linkTo}>
        <OverlayTrigger placement='bottom' overlay={<Tooltip id="button-tooltip">{props.label}</Tooltip>}>
          {props.icon}
        </OverlayTrigger> <b className='d-md-none'>{props.label}</b>
      </Nav.Link>
  )
}

export default Header