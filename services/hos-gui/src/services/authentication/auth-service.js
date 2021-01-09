import {AuthAPI} from '../../api/hos-service-api'

export default class AuthService {

  static async login(loginForm) {
    let response = await AuthAPI.login(loginForm)
    let status = await response.status
    if (status === 200) {
      let token = await response.text()
      localStorage.setItem("jwtToken", token)
      return true
    } else {
      return false
    }
  }

  static async me() {
    let response = await AuthAPI.loggedInUser()
    let status = await response.status
    if (status === 200) {
      return await response.json()
    } else {
      localStorage.removeItem("jwtToken")
    }
  }

  static logout() {
    localStorage.clear()
    window.location.href = '/';
  }

}


