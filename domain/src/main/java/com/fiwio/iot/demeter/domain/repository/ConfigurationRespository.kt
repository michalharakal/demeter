package com.fiwio.iot.demeter.domain.repository

import com.fiwio.iot.demeter.domain.model.BranchValveParameters
import io.reactivex.Completable
import io.reactivex.Single

interface ConfigurationRespository {
    fun getValveParameters(branchId: Int): Single<BranchValveParameters>
    fun setValveParameters(branchId: Int, newValues: BranchValveParameters): Completable
}
