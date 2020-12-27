import React from 'react';
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom";
import ProtectedRoute from "./components/protected-route/protected-route";
import Login from "./components/login/login";
import Board from "./components/board/board";

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
