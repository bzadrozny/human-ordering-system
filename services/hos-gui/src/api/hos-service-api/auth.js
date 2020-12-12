import HosServiceApi from "./hos-api";

export default class Auth extends HosServiceApi {

  static async login(loginForm) {
    const url = '/login';
    return await fetch(this.BASE_HOST + url, {
      method: 'POST',
      headers: this._.createHeaders(),
      body: JSON.stringify(loginForm)
    });
  }

  static async loggedInUser() {
    const URL = '/user/me';
    return await fetch(this.BASE_HOST + URL, {
      method: 'GET',
      headers: this._.createHeaders()
    });
  }

  static async logout() {
    const url = '/logout';
    return fetch(this.BASE_HOST + url, {
      method: 'POST',
      headers: this._.createHeaders()
    })
  }

}