package com.fiwio.iot.demeter.data.repository

import com.fiwio.iot.demeter.data.source.DemeterDataSourceFactory
import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.repository.DemeterRepository
import io.reactivex.Single
import javax.inject.Inject

class DemeterDataRepository @Inject constructor(private val demeterDataSourceFactory: DemeterDataSourceFactory) :
        DemeterRepository {
    override fun getDemeterImage(): Single<Demeter> {
        return demeterDataSourceFactory.retrieveDataStore().getDemeterImage()
    }
}