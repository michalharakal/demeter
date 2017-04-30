package com.fiwio.iot.demeter.remote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fiwio.iot.demeter.api.model.Demeter;
import com.fiwio.iot.demeter.api.model.Relay;
import com.fiwio.iot.demeter.app.DemeterApplication;
import com.fiwio.iot.demeter.app.EndpoitUrlProvider;
import com.fiwio.iot.demeter.remote.di.RemoteControlModule;
import com.fiwo.iot.demeter.smart.R;

import javax.inject.Inject;

public class RemoteControlActivity extends AppCompatActivity implements RemoteControlContract.View {

    @Inject
    public RemoteControlPresenter presenter;

    @Inject
    protected EndpoitUrlProvider endpoitUrlProvider;

    private ProgressBar progressBar;
    private RecyclerView list;
    private TextView url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        inject();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            endpoitUrlProvider.setUrl(bundle.getString("host"));
        }

        renderView();
        init();
        presenter.onStart();
    }

    private void inject() {
        DemeterApplication.get(this).getAppComponent().plus(new RemoteControlModule(this)).injects
                (this);
    }

    public void renderView() {
        list = (RecyclerView) findViewById(R.id.control_list);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        url = (TextView) findViewById(R.id.url);
    }

    public void init() {
        list.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        RemoteControlListAdapter adapter = new RemoteControlListAdapter(getApplicationContext(),
                demeter,
                new RemoteControlListAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Relay item) {
                        presenter.switchRelay(item.getName(), "OFF".equals(item.getValue()) ? true : false);
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
