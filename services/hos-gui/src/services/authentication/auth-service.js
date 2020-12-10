import {login as hosAPI} from '../hos-service-api'

export default class AuthService {

    static async login(loginForm) {
        let response = await hosAPI.login(loginForm)
        let status = await response.status
        if (status === 200) {
            let token = await response.text()
            localStorage.setItem("jwtToken", token)
            return true
        } else {
            return false
        }
    }

    static logout() {
        localStorage.removeItem("jwtToken")
    }

}


