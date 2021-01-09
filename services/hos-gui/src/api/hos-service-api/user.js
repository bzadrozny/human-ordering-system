import HosServiceApi from "./hos-api";
import axios from "axios";

export default class User extends HosServiceApi {

  static CONTEXT_URL = '/user'

  static async allUsers() {
    return await axios.get(this.BASE_HOST + this.CONTEXT_URL, {
      headers: this._.createHeaders()
    })
  }

  static async userByID(id) {
    const SPEC_URL = '/' + id
    return await axios.get(this.BASE_HOST + this.CONTEXT_URL + SPEC_URL, {
      headers: this._.createHeaders()
    })
  }

}