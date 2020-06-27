package com.fiwio.iot.demeter.presentation.feature.splash

import com.fiwio.iot.demeter.domain.features.splash.FindDemeter
import com.fiwio.iot.demeter.domain.model.DemeterSearchDnsInfo
import com.fiwio.iot.demeter.domain.model.DnsSearchResult
import com.fiwio.iot.demeter.presentation.mapper.DnsLookupStateMapper
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class SplashPresenter @Inject constructor(private val findDemeter: FindDemeter, private val dnsLookupStateMapper: DnsLookupStateMapper) :
        SplashContract.Presenter {

    internal var view: SplashContract.View? = null

    override fun attachView(view: SplashContract.View) {
        this.view = view
        findDemeter.execute(HandleDemeterSearcshResult())
    }


    override fun detachView() {
        findDemeter.dispose()
    }

    override fun destroy() {
    }

    inner class HandleDemeterSearcshResult : DisposableObserver<DemeterSearchDnsInfo>() {
        override fun onComplete() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onNext(t: DemeterSearchDnsInfo) {
            if (t.dnsSearchResult == DnsSearchResult.NOT_FOUND) {
                view?.startOffline()
            } else {
                view?.setData(dnsLookupStateMapper.mapToView(t))
            }
        }

        override fun onError(e: Throwable) {
            view?.startOffline()
        }
    }
}

