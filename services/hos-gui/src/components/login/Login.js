import React, {Component} from 'react';
import AuthService from "../../services/authentication/auth-service";
import {Redirect} from "react-router-dom";

class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            loggedIn: false,
            failedLogin: false,
            processLogin: false,
            loginForm: {
                login: "",
                password: ""
            }
        };
    }

    handleFormChange = event => {
        let loginForm = this.state.loginForm;
        loginForm[event.target.name] = event.target.value;
        this.setState({
            loginForm: loginForm,
        });
    };

    handleSubmit = async (event) => {
        event.preventDefault();
        this.setState({
            processLogin: true
        })
        let loginForm = {...this.state.loginForm};
        let loggedIn = await AuthService.login(loginForm)
        this.setState({
            loggedIn: loggedIn,
            failedLogin: !loggedIn,
            processLogin: false,
        })
    }

    renderRedirect = () => {
        if (this.state.loggedIn) {
            return <Redirect to='/board'/>
        }
    }

    renderFailedLogin = () => {
        if (this.state.failedLogin) {
            return (
                <div>
                    Błędne dane logowania
                </div>
            )
        }
    }

    render() {
        return (
            <div>
                {this.renderRedirect()}
                <div>
                    <p> Logowanie </p>
                </div>
                {this.renderFailedLogin()}
                <form onSubmit={this.handleSubmit}>
                    <input
                        type="text"
                        name="login"
                        onChange={this.handleFormChange}
                        placeholder="Enter Username"
                        disabled={this.state.processLogin}
                    />
                    <input
                        type="password"
                        name="password"
                        onChange={this.handleFormChange}
                        placeholder="Enter Password"
                        disabled={this.state.processLogin}
                    />
                    <input
                        type="submit"
                        value="Login"
                        disabled={this.state.processLogin}
                    />
                </form>
            </div>
        )
    }
}

export default Login
