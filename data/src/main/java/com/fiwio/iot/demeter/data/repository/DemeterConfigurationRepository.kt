package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.domain.model.BranchValveParameters
import com.fiwio.iot.demeter.domain.repository.ConfigurationRespository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


class DemeterConfigurationRepository @Inject constructor() : ConfigurationRespository {

    private val tenMinutesInSeconds = 10 * 60L
    private val twentySeconds = 20L


    private var defaultValveParameters: BranchValveParameters =
            BranchValveParameters(twentySeconds, tenMinutesInSeconds, tenMinutesInSeconds)

    override fun getValveParameters(branchId: Int): Single<BranchValveParameters> {
        return Single.just(defaultValveParameters)
    }

    override fun setValveParameters(branchId: Int, newValues: BranchValveParameters): Completable {
        defaultValveParameters = newValues
        return Completable.complete()
    }
}
