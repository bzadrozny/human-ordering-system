import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import CommissionBoard from "../commissions/CommissionBoard";
import AdministrationBoard from "../administartion/AdministrationBoard";
import SettingBoard from "../settings/SettingBoard";
import StatisticsBoard from "../statistics/StatisticsBoard";

class Board extends Component {

    constructor(props) {
        super(props)
        this.state = {
            user: null
        }
    }

    componentDidMount() {

    }

    render() {
        return (
            <div>
                <Navbar/>
                <Router>
                    <Switch>
                        <Route path="/board/setting" component={SettingBoard}/>
                        <Route path="/board/statistic" component={StatisticsBoard}/>
                        <Route path="/board/administration" component={AdministrationBoard}/>
                        <Route component={CommissionBoard}/>
                    </Switch>
                </Router>
                <Footer/>
            </div>
        )
    }
}

let Navbar = (props) => {

    return (
        <div>
            Header
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