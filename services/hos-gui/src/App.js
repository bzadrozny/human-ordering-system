import React from 'react';
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom";
import ProtectedRoute from "./components/protectedRoute/ProtectedRoute";
import Login from "./components/login/Login";
import Board from "./components/board/Board";
// import 'react-bootstrap/css/bootstrap.min.css';
import './style/App.css';

let App = () => {
    return (
        <Router>
            <Switch>
                <ProtectedRoute path="/board" component={Board}/>
                <Route path="/" exact={true} render={(props) => <Login {...props} />}/>
                <Redirect to="/"/>
            </Switch>
        </Router>
    );
}

export default App;
