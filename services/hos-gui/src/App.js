import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import ProtectedRoute from "./components/protectedRoute/ProtectedRoute";
import Login from "./components/login/Login";
import Board from "./components/board/Board";
import './style/App.css';

class App extends Component {
    render() {
        return (
            <div className="App">
                <Router>
                    <Switch>
                        <ProtectedRoute path="/board" component={Board}/>
                        <Route component={Login}/>
                    </Switch>
                </Router>
            </div>
        );
    }

}

export default App;
