import React from "react";
import {RiCopyrightLine} from "react-icons/all";

let Footer = (props) => {

  const link = <a
      style={{color: 'lightgrey'}}
      target='_blank'
      href='https://www.linkedin.com/in/bart%C5%82omiej-zadro%C5%BCny-776468125/'>
    BARTŁOMIEJ ZADROŻNY
  </a>

  return (
      <footer className='pt-3 text-center align-content-center' style={{color: 'lightgrey'}}>
        <RiCopyrightLine fontSize='1.3em'/>{link} 2021
        <br/>ALL RIGHTS RESERVED
      </footer>
  )

}

export default Footer