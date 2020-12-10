import HosServiceApi from "./hos-api";

export default class Commission extends HosServiceApi {

  CONTEXT_URL = '/commission'

  async allCommissions(filter) {
    return await fetch(this.BASE_HOST + this.CONTEXT_URL, {
      method: 'GET',
      headers: this.createHeaders(),
      body: JSON.stringify(filter)
    });
  }

  async commissionsById(id) {
    const SPEC_URL = '/' + id
    return await fetch(this.BASE_HOST + this.CONTEXT_URL + SPEC_URL, {
      method: 'GET',
      headers: this.createHeaders()
    });
  }

}