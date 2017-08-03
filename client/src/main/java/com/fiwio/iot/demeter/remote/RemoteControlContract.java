package com.fiwio.iot.demeter.remote;

import com.fiwio.iot.BasePresenter;
import com.fiwio.iot.BaseView;
import com.fiwo.iot.demeter.api.model.Demeter;

public class RemoteControlContract {
    public interface View extends BaseView<Presenter> {

        void showWait();

        void removeWait();

        void onFailure(String appErrorMessage);

        void setList(Demeter demeter);

        void showEndpoint(String url);
    }

    interface Presenter extends BasePresenter {

        void switchRelay(String name, boolean on);
    }
}
