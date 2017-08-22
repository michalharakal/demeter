package com.fiwio.iot.demeter.app.demeter.remote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fiwio.iot.demeter.app.app.EndpoitUrlProvider;
import com.fiwio.iot.demeter.app.demeter.app.DemeterApplication;
import com.fiwio.iot.demeter.app.demeter.features.remote.RemoteControlContract;
import com.fiwio.iot.demeter.app.demeter.features.remote.RemoteControlPresenter;
import com.fiwio.iot.demeter.app.demeter.remote.di.RemoteControlModule;
import com.fiwo.iot.demeter.api.model.Demeter;
import com.fiwo.iot.demeter.api.model.Relay;
import com.fiwo.iot.demeter.app.R;

import javax.inject.Inject;

public class RemoteControlFragment extends Fragment implements RemoteControlContract.View {

    @Inject
    public RemoteControlPresenter presenter;

    @Inject
    protected EndpoitUrlProvider endpoitUrlProvider;

    private ProgressBar progressBar;
    private RecyclerView list;
    private TextView url;


    private View mContent;
    private TextView mTextView;
    private FloatingActionButton fab;

    public static Fragment newInstance() {
        Fragment frag = new RemoteControlFragment();
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_remote, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize views
        mContent = view;

        inject();
        renderView();
        init();

        presenter.onStart();

    }

    private void inject() {
        DemeterApplication.get(getContext()).getAppComponent().plus(new RemoteControlModule(this)).injects
                (this);
    }

    public void renderView() {
        getActivity().setTitle(getString(R.string.manual_control));
        list = (RecyclerView) mContent.findViewById(R.id.control_list);
        progressBar = (ProgressBar) mContent.findViewById(R.id.progress);
        url = (TextView) mContent.findViewById(R.id.url);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_switch_off);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.switchAllOff();
            }
        });
    }

    public void init() {
        list.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {
        url.setText(appErrorMessage);
    }

    @Override
    public void setList(Demeter demeter) {
        url.setVisibility(View.GONE);
        RemoteControlListAdapter adapter = new RemoteControlListAdapter(getContext(), demeter,
                new RemoteControlListAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Relay item) {
                        presenter.switchRelay(item.getName(), "OFF".equals(item.getValue()) ?
                                true : false);
                    }
                });

        list.setAdapter(adapter);
    }

    @Override
    public void showEndpoint(String url) {
        this.url.setText(url);
    }


    @Override
    public void setPresenter(RemoteControlContract.Presenter presenter) {

    }
}