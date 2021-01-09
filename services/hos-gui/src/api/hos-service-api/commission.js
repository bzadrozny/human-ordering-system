import HosServiceApi from "./hos-api";
import axios from 'axios';

export default class Commission extends HosServiceApi {

  static CONTEXT_URL = '/commission'

  static async create(form) {
    return await axios.post(this.BASE_HOST + this.CONTEXT_URL, form, {
      headers: this._.createHeaders()
    })
  }

  static async edit(form) {
    const SPEC_URL = '/' + form.id
    return await axios.put(this.BASE_HOST + this.CONTEXT_URL + SPEC_URL, form, {
      headers: this._.createHeaders()
    })
  }

  static async delete(id) {
    const SPEC_URL = '/' + id
    return await axios.delete(this.BASE_HOST + this.CONTEXT_URL + SPEC_URL, {
      headers: this._.createHeaders()
    })
  }

  static async send(id) {
    const SPEC_URL = '/' + id + '/send'
    return await axios.post(this.BASE_HOST + this.CONTEXT_URL + SPEC_URL, null,{
      headers: this._.createHeaders()
    })
  }

  static async decision(decision) {
    const SPEC_URL = '/' + decision.id + '/decision'
    return await axios.post(this.BASE_HOST + this.CONTEXT_URL + SPEC_URL, decision,{
      headers: this._.createHeaders()
    })
  }

  static async allCommissions(filter) {
    return await axios.get(this.BASE_HOST + this.CONTEXT_URL, {
      params: filter,
      headers: this._.createHeaders()
    })
  }

  static async commissionsById(id) {
    const SPEC_URL = '/' + id
    return await axios.get(this.BASE_HOST + this.CONTEXT_URL + SPEC_URL, {
      headers: this._.createHeaders()
    });
  }

}