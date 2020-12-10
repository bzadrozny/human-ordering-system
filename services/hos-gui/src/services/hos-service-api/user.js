import HosServiceApi from "./hos-api";

export default class User extends HosServiceApi {

  static async loggedInUser() {
    const URL = '/user/details';
    return await fetch(this.BASE_HOST + URL, {
      method: 'GET',
      headers: this.createHeaders()
    });
  }

  static async allUsers() {
    const URL = '/user';
    return await fetch(this.BASE_HOST + URL, {
      method: 'GET',
      headers: this.createHeaders()
    });
  }

  static async userByID(id) {
    const URL = '/user';
    const ID = '/' + id
    return await fetch(this.BASE_HOST + URL + ID, {
      method: 'GET',
      headers: this.createHeaders()
    });
  }

}