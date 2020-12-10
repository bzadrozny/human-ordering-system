import React, {Component} from 'react';
import {Redirect} from "react-router-dom";

class ProtectedRoute extends Component {

    render() {
        const Component = this.props.component
        let token = localStorage.getItem('jwtToken')
        const isAuthenticated = token != null

        return isAuthenticated ? (
            <Component/>
        ) : (
            <Redirect to='/'/>
        )
    }

}

export default ProtectedRoute
