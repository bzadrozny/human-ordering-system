import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Redirect, Switch} from "react-router-dom";
import CommissionBoard from "../commissions/CommissionBoard";
import AdministrationBoard from "../administartion/AdministrationBoard";
import SettingBoard from "../settings/SettingBoard";
import StatisticsBoard from "../statistics/StatisticsBoard";
import AuthService from "../../services/authentication/auth-service";

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
            <div>
                {this.renderRedirect()}
                <Navbar user={this.state.user}/>
                <Router>
                    <Switch>
                        <Route path="/board/setting" component={SettingBoard}/>
                        <Route path="/board/statistic" component={StatisticsBoard}/>
                        <Route path="/board/administration" component={AdministrationBoard}/>
                        <Route path="/board/commission" component={CommissionBoard}/>
                        <Redirect to="/"/>
                    </Switch>
                </Router>
                <Footer/>
            </div>
        )
    }
}

let Navbar = (props) => {
    let user = props.user
    return (
        <div>
            {user ? user.email : 'dupa'}
            <button onClick={AuthService.logout} >Logout</button>
        </div>
    )

}
let Footer = (props) => {

    return (
        <div>
            Footer
        </div>
    )

}


export default Board