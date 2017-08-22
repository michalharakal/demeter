package com.fiwio.iot.demeter.app.demeter.scheduler;

import android.content.Intent;
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

import com.fiwio.iot.demeter.app.demeter.addtask.AddSchedulerTaskActivity;
import com.fiwio.iot.demeter.app.demeter.app.DemeterApplication;
import com.fiwio.iot.demeter.app.demeter.features.scheduler.SchedulerContract;
import com.fiwio.iot.demeter.app.demeter.scheduler.di.SchedulerModule;
import com.fiwo.iot.demeter.api.model.ScheduledEvents;
import com.fiwo.iot.demeter.app.R;

import javax.inject.Inject;

public class SchedulerFragment extends Fragment implements SchedulerContract.View {

    @Inject
    public SchedulerContract.Presenter presenter;

    private ProgressBar progressBar;
    private RecyclerView list;
    private TextView url;


    private View mContent;
    private FloatingActionButton fab;

    public static Fragment newInstance() {
        Fragment frag = new SchedulerFragment();
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scheduler, container, false);
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

    @Override
    public void onResume() {
        super.onResume();
        presenter.onStart();
    }

    private void inject() {
        DemeterApplication.get(getContext()).getAppComponent().plus(new SchedulerModule(this)).injects
                (this);
    }

    public void renderView() {

        list = (RecyclerView) mContent.findViewById(R.id.control_list);
        progressBar = (ProgressBar) mContent.findViewById(R.id.progress);
        url = (TextView) mContent.findViewById(R.id.url);

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTask();
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
    public void setList(ScheduledEvents events) {
        getActivity().setTitle(getString(R.string.automatic_tasks));
        url.setVisibility(View.GONE);
        SchedulerListAdapter adapter = new SchedulerListAdapter(getContext(), events);

        list.setAdapter(adapter);
    }

    @Override
    public void showAddTask() {
        Intent intent = new Intent(getContext(), AddSchedulerTaskActivity.class);
        startActivityForResult(intent, AddSchedulerTaskActivity.REQUEST_ADD_TASK);

    }


    @Override
    public void setPresenter(SchedulerContract.Presenter presenter) {

    }
}