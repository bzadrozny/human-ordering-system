import HosServiceApi from "./hos-api";
import axios from 'axios';

export default class Organisation extends HosServiceApi {

  static CONTEXT_URL = '/organisation'

  static async allOrganisations() {
    return await axios.get(this.BASE_HOST + this.CONTEXT_URL, {
      headers: this._.createHeaders()
    });
  }

  static async organisationById(id) {
    const ID = '/' + id
    return await axios.get(this.BASE_HOST + this.CONTEXT_URL + ID, {
      headers: this._.createHeaders()
    });
  }

}