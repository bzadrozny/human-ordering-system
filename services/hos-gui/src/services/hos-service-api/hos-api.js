export default class HosServiceApi {

  static headers = {
    'Accept': 'application/json',
    'Content-Type': 'application/json',

  };

  static BASE_HOST = 'http://localhost:8080'

  static createHeaders() {
    let token = localStorage.getItem('token')
    return token ? {
      ...this.headers,
      'Authorization': 'Bearer ' + token
    } : this.headers;
  }

}