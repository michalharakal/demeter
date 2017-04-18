package com.fiwio.iot.demeter.remote;

import com.fiwio.iot.BasePresenter;
import com.fiwio.iot.BaseView;
import com.fiwio.iot.demeter.api.model.Demeter;

public class RemoteControlContract {
    public interface View extends BaseView<Presenter> {

        void showWait();

        void removeWait();

        void onFailure(String appErrorMessage);

        void setList(Demeter demeter);
    }

    interface Presenter extends BasePresenter {

        void setRelay(String name, boolean on);
    }
}
