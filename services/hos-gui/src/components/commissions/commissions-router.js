import React from 'react';
import {Route} from "react-router-dom";
import CommissionsBoard from "./commissions-board";
import CommissionForm from "./form/commission-form";
import CommissionDetails from "./details/commission-details";
import './style/commissions.css'

const CommissionsRouter = () => (
    <>
      <Route exact path='/board/commission' component={CommissionsBoard}/>
      <Route exact path='/board/commission/new' component={CommissionForm}/>
      <Route exact path="/board/commission/:id(\d+)" component={CommissionDetails}/>
      <Route exact path='/board/commission/:id(\d+)/edit' component={CommissionForm}/>
    </>
)


export default CommissionsRouter