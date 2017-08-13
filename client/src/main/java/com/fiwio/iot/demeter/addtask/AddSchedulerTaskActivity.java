package com.fiwio.iot.demeter.addtask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fiwio.iot.demeter.addtask.di.AddSchedulerTaskModule;
import com.fiwio.iot.demeter.app.DemeterApplication;
import com.fiwio.iot.demeter.features.addtask.AddSchedulerTaskContract;
import com.fiwo.iot.demeter.smart.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import javax.inject.Inject;

public class AddSchedulerTaskActivity extends AppCompatActivity implements AddSchedulerTaskContract.View, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final int REQUEST_ADD_TASK = 123;

    @Inject
    public AddSchedulerTaskContract.Presenter presenter;
    private Button pickDate;
    private TextView date;
    private TextView time;
    private Button pickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inject();

        date = (TextView) findViewById(R.id.date_value);
        time = (TextView) findViewById(R.id.time_value);


        pickDate = (Button) findViewById(R.id.bt_pick_date);
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = DatePickerDialog.newInstance(AddSchedulerTaskActivity.this,
                        presenter.getYear(),
                        presenter.getMonth() - 1,
                        presenter.getDay()
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        pickTime = (Button) findViewById(R.id.bt_pick_time);
        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dpd = TimePickerDialog.newInstance(AddSchedulerTaskActivity.this,
                        presenter.getHour(),
                        presenter.getMinute(),
                        true
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });


        presenter.onStart();
        setTitle(getString(R.string.new_task));

    }

    private void inject() {
        DemeterApplication.get(this).getAppComponent().plus(new AddSchedulerTaskModule(this)).injects(this);
    }

    @Override
    public void setPresenter(AddSchedulerTaskContract.Presenter presenter) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        presenter.putDate(year, monthOfYear + 1, dayOfMonth);

    }

    @Override
    public void showDate(String value) {
        date.setText(value);
    }

    @Override
    public void showTime(String value) {
        time.setText(value);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        presenter.putTime(hourOfDay, minute, second);
    }
}
