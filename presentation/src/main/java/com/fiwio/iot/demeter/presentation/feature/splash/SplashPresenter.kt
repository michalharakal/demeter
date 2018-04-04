package com.fiwio.iot.demeter.presentation.feature.splash

import com.fiwio.iot.demeter.domain.features.splash.FindDemeter
import com.fiwio.iot.demeter.domain.model.Demeter
import com.fiwio.iot.demeter.domain.model.DemeterSearchDnsInfo
import com.fiwio.iot.demeter.presentation.feature.manual.ActuatorsListContract
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class SplashPresenter @Inject constructor(val findDemeter: FindDemeter) :
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

        }

        override fun onError(exception: Throwable) {
            view?.enterUrlByHand()
        }
    }



}

