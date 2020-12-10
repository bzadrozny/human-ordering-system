import HosServiceApi from "./hos-api";

export default class Login extends HosServiceApi {

  static async login(loginForm) {
    const url = '/login';
    return await fetch(this.BASE_HOST + url, {
      method: 'POST',
      headers: this.createHeaders(),
      body: JSON.stringify(loginForm)
    });
  }

  async logout() {
    const url = '/logout';
    return fetch(this.BASE_HOST + url, {
      method: 'POST',
      headers: this.createHeaders()
    })
  }

}