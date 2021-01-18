import HosServiceApi from "./hos-api";
import axios from "axios";

export default class Contract extends HosServiceApi {

  static CONTEXT_URL = (commissionId, recordId) => {
    return '/commission/{commissionId}/records/{recordId}/contract'
        .replace('{commissionId}', commissionId)
        .replace('{recordId}', recordId)
  }

  static async findById(commission, record, id) {
    const URL = this.BASE_HOST + this.CONTEXT_URL(commission, record) + '/' + id
    return await axios.get(URL, {
      headers: this._.createHeaders()
    })
  }

  static async create(form) {
    const URL = this.BASE_HOST + this.CONTEXT_URL(form.commission, form.commissionRecord)
    return await axios.post(URL, form, {
      headers: this._.createHeaders()
    })
  }

  static async edit(form) {
    const URL = this.BASE_HOST + this.CONTEXT_URL(form.commission, form.commissionRecord) + '/' + form.id
    return await axios.put(URL, form, {
      headers: this._.createHeaders()
    })
  }

}