package com.fiwio.iot.demeter.app.data;


import com.fiwo.iot.demeter.api.model.Demeter;
import com.fiwo.iot.demeter.api.model.DigitalOutputs;

public interface CmlRepository {

    void getDemeter(final GetDemeterCallback callback);

    void switchRelay(final String name, final boolean on, final PostDemeterCallback callback);

    void switchAllOff(final PostDemeterCallback callback);

    interface GetDemeterCallback {
        void onSuccess(Demeter demeter);

        void onError(NetworkError networkError);
    }

    interface PostDemeterCallback {
        void onSuccess(DigitalOutputs demeter);

        void onError(NetworkError networkError);
    }

}
