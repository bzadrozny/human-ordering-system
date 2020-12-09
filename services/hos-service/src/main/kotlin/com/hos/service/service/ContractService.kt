package com.hos.service.service

import com.hos.service.model.form.ContractForm
import com.hos.service.model.record.ContractDetailsRecord

interface ContractService {

    fun findContractDetailsById(contractId: Long): ContractDetailsRecord

    fun registerContract(form: ContractForm): ContractDetailsRecord

    fun modifyContract(form: ContractForm): ContractDetailsRecord

}