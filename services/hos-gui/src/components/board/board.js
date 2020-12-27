import React, {Component} from 'react';
import {Redirect, Route} from "react-router-dom";
import Header from "./board-header";
import Footer from "./board-footer";
import AuthService from "../../services/authentication/auth-service";
import SettingBoard from "../settings/setting-board";
import StatisticsBoard from "../statistics/statistics-board";
import AdministrationBoard from "../administartion/administration-board";
import CommissionsRouter from "../commissions/commissions-router";
import UserContext from "../../context/user-context";
import './board.css'

class Board extends Component {

  constructor(props) {
    super(props)
    this.state = {
      user: null,
      tokenInvalidated: false
    }
  }

  componentDidMount = async () => {
    let user = await AuthService.me()
    if (user == null) {
      this.setState({tokenInvalidated: true})
    } else {
      this.setState({user})
    }
  }

  renderRedirect = () => {
    if (this.state.tokenInvalidated) {
      return <Redirect to={{
        pathname: '/',
        state: {tokenInvalidated: true}
      }}/>
    }
  }

  render() {
    return (
        <UserContext.Provider value={this.state.user}>
          <div className='board'>
            {this.renderRedirect()}
            <Header/>
            <main>
              <Route path="/board/setting" component={SettingBoard}/>
              <Route path="/board/statistic" component={StatisticsBoard}/>
              <Route path="/board/administration" component={AdministrationBoard}/>
              <Route path="/board/commission" component={CommissionsRouter}/>
            </main>
            <Footer/>
          </div>
        </UserContext.Provider>
    )
  }
}

export default Board