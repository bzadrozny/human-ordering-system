import React, {Component} from 'react';
import AuthService from "../../services/authentication/auth-service";
import {Redirect} from "react-router-dom";
import './style/login.css';

import {Alert, Button, Card, Carousel, Container, Form} from "react-bootstrap";

class Login extends Component {

    constructor(props) {
        super(props);
        let locationState = props.location.state
        let tokenInvalidated = locationState != null ? locationState.tokenInvalidated : false
        this.state = {
            loggedIn: localStorage.getItem("jwtToken") != null,
            tokenInvalidated: tokenInvalidated,
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
            return <Redirect to='/board/commission'/>
        }
    }

    renderTokenInvalidated = () => {
        if (this.state.tokenInvalidated) {
            return (
                <Alert
                    variant='danger'
                    onClick={() => this.setState({tokenInvalidated: false})}
                    dismissible
                >
                    Wygasły dane logowania
                </Alert>
            )
        }
    }

    renderFailedLogin = () => {
        if (this.state.failedLogin) {
            return (
                <Alert
                    variant='danger'
                    onClick={() => this.setState({failedLogin: false})}
                    dismissible
                >
                    Błędne dane logowania
                </Alert>
            )
        }
    }

    render() {
        return (
            <div className='login-page'>
                {this.renderRedirect()}
                <HosInfo/>
                <HosLogin
                    renderTokenInvalidated={this.renderTokenInvalidated}
                    renderFailedLogin={this.renderFailedLogin}
                    handleFormChange={this.handleFormChange}
                    handleSubmit={this.handleSubmit}
                    processLogin={this.state.processLogin}
                />
            </div>
        )
    }
}

let HosInfo = () => {
    return (
        <Carousel className='hos-info'>
            <Carousel.Item className='hos-info-title-card'>
                <h1>Human Ordering System</h1>
                <br/>
                <h2>Twój system rekrutacyjny!</h2>
            </Carousel.Item>
            <Carousel.Item className='hos-info-card'>
                <h3>Dla klienta!</h3>
                <div className='hos-info-desc'>
                    <p>Idealne miejsce, żeby szybko i sprawnie zamówić rekrutację na pracowników</p>
                    <p>Śledzić proces realizacji zamówienia</p>
                    <p>Negocjować realizację zamówienia</p>
                    <p>Przeglądać historię rekrutacji oraz ich przebig</p>
                    <p>A to wszystko w bezpieczny sposób w jednym miejscu!</p>
                </div>
            </Carousel.Item>
            <Carousel.Item className='hos-info-card'>
                <h3>Dla obsługi klienta!</h3>
                <div className='hos-info-desc'>
                    <p>Pogląd złożonych zamówień na rekrutacje</p>
                    <p>Akceptacja zamówień oparta na ich statusach</p>
                    <p>Nadzór nad realizacją zamówienia przez zespół rekruterów</p>
                    <p>Klarowne rozliczenie zamówienia z klientem</p>
                    <p>Podsumowania tygodniowe i miesięczne</p>
                    <p>A to wszystko w bezpieczny sposób w jednym miejscu!</p>
                </div>
            </Carousel.Item>
            <Carousel.Item className='hos-info-card'>
                <h3>Dla obsługi rekrutera!</h3>
                <div className='hos-info-desc'>
                    <p>Zespołowa realizacja zamówienia</p>
                    <p>Wspólna baza dostępnych kandydatów</p>
                    <p>Szybki pogląd stanu realizacji</p>
                    <p>Spójny przegląd wprowadzonych kotraktów</p>
                    <p>A to wszystko w bezpieczny sposób w jednym miejscu!</p>
                </div>
            </Carousel.Item>
        </Carousel>
    )
}

let HosLogin = (props) => {
    return <Container className='hos-login'>
        <Card className='login-card'>
            <Card.Header className='login-card-title'/>
            <Card.Body>
                {props.renderTokenInvalidated()}
                {props.renderFailedLogin()}
                <Form onSubmit={props.handleSubmit}>
                    <Form.Group>
                        <Form.Control
                            type='text'
                            name="login"
                            placeholder="wprowadź login"
                            onChange={props.handleFormChange}
                            disabled={props.processLogin}
                        />
                    </Form.Group>
                    <Form.Group>
                        <Form.Control
                            type='password'
                            name="password"
                            placeholder="wprowadź hasło"
                            onChange={props.handleFormChange}
                            disabled={props.processLogin}
                        />
                    </Form.Group>
                    <Button type="submit" variant='info' className='w-50 m-auto' disabled={props.processLogin}>
                        Zaloguj
                    </Button>
                </Form>
            </Card.Body>
        </Card>
    </Container>;
}

export default Login
