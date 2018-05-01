package com.fiwio.iot.demeter.presentation.feature.splash

import com.fiwio.iot.demeter.domain.features.splash.FindDemeter
import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.model.DemeterSearchDnsInfo
import com.fiwio.iot.demeter.domain.model.DnsSearchResult
import com.fiwio.iot.demeter.presentation.feature.manual.ActuatorsListContract
import com.fiwio.iot.demeter.presentation.mapper.DnsLookupStateMapper
import com.fiwio.iot.demeter.presentation.model.DnsLookupState
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class SplashPresenter @Inject constructor(val findDemeter: FindDemeter, val dnsLookupStateMapper: DnsLookupStateMapper) :
        SplashContract.Presenter {

    internal var view: SplashContract.View? = null

    override fun attachView(view: SplashContract.View) {
        this.view = view
        findDemeter.execute(HandleDemeterFound())
    }

    override fun detachView() {
        findDemeter.dispose()
    }

    override fun destroy() {
    }

    override fun startWithUrl(ip: String) {

    }

    inner class HandleDemeterFound : DisposableSingleObserver<DemeterSearchDnsInfo>() {

        override fun onSuccess(t: DemeterSearchDnsInfo) {
            if (t.dnsSearchResult == DnsSearchResult.NOT_FOUND) {
                view?.enterUrlByHand()
            } else {
                view?.setData(dnsLookupStateMapper.mapToView(t))
            }

        }

        override fun onError(exception: Throwable) {
            view?.enterUrlByHand()
        }
    }


}

