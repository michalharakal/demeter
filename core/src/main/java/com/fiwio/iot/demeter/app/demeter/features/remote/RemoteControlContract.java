package com.fiwio.iot.demeter.app.demeter.features.remote;

import com.fiwio.iot.demeter.app.mvp.BasePresenter;
import com.fiwio.iot.demeter.app.mvp.BaseView;
import com.fiwo.iot.demeter.api.model.Demeter;

public class RemoteControlContract {
    public interface View extends BaseView<Presenter> {

        void showWait();

        void removeWait();

        void onFailure(String appErrorMessage);

        void setList(Demeter demeter);

        void showEndpoint(String url);
    }

    public interface Presenter extends BasePresenter {
        void switchRelay(String name, boolean on);

        void switchAllOff();
    }
}
