import HosServiceApi from "./hos-api";

export default class User extends HosServiceApi {

  static async allUsers() {
    const URL = '/user';
    return await fetch(this.BASE_HOST + URL, {
      method: 'GET',
      headers: this._.createHeaders()
    });
  }

  static async userByID(id) {
    const URL = '/user';
    const ID = '/' + id
    return await fetch(this.BASE_HOST + URL + ID, {
      method: 'GET',
      headers: this._.createHeaders()
    });
  }

}