import React from 'react';
import {Route} from "react-router-dom";
import CommissionsBoard from "./commissions-board";
import CommissionForm from "./form/commission-form";
import CommissionsDetails from "./details/commissions-details";
import './style/commissions.css'

const CommissionsRouter = () => (
    <>
      <Route exact path='/board/commission' component={CommissionsBoard}/>
      <Route exact path='/board/commission/new' component={CommissionForm}/>
      <Route exact path="/board/commission/:id(\d+)" component={CommissionsDetails}/>
      <Route exact path='/board/commission/:id(\d+)/edit' component={CommissionForm}/>
    </>
)


export default CommissionsRouter