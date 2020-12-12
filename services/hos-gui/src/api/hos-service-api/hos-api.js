export default class HosServiceApi {

  static _ = new HosServiceApi()

  headers = {
    'Accept': 'application/json',
    'Content-Type': 'application/json',

  };

  static BASE_HOST = 'http://localhost:8080'

  createHeaders() {
    let token = localStorage.getItem('jwtToken')
    return token ? {
      ...this.headers,
      'Authorization': 'Bearer ' + token
    } : this.headers;
  }

}