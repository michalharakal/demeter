package com.fiwio.iot.data;

import com.fiwio.iot.demeter.api.DemeterApi;
import com.fiwio.iot.demeter.api.NetworkError;
import com.fiwio.iot.demeter.api.model.Demeter;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DemeterRepository implements CmlRepository {
    private final DemeterApi networkService;

    public DemeterRepository(DemeterApi networkService) {
        this.networkService = networkService;
    }

    @Override
    public Subscription getDemeter(final GetDemeterCallback callback) {

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
}
