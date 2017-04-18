package com.fiwio.iot.demeter.api;

import com.fiwio.iot.demeter.api.model.Demeter;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public final class DemeterService {
    private final DemeterApi networkService;

    @Inject
    public DemeterService(DemeterApi networkService) {
        this.networkService = networkService;
    }

    public Subscription get(final GetCityListCallback callback) {

        return networkService.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Demeter>>() {
                    @Override
                    public Observable<? extends Demeter> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<Demeter>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(Demeter cityListResponse) {
                        callback.onSuccess(cityListResponse);

                    }
                });
    }

    public interface GetCityListCallback {
        void onSuccess(Demeter demeter);

        void onError(NetworkError networkError);
    }
}
