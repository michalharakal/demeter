package com.fiwio.iot.data;

import com.fiwio.iot.demeter.api.NetworkError;
import com.fiwio.iot.demeter.api.model.Demeter;


public interface CmlRepository {

    void getDemeter(final GetDemeterCallback callback);

    interface GetDemeterCallback {
        void onSuccess(Demeter demeter);

        void onError(NetworkError networkError);
    }
}
