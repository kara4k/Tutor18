package com.kara4k.tutor18.view.activities;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Button;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.di.DaggerViewComponent;
import com.kara4k.tutor18.di.modules.ViewModule;
import com.kara4k.tutor18.other.FormatUtils;
import com.kara4k.tutor18.presenter.EventsPresenter;
import com.kara4k.tutor18.view.EventsIF;
import com.kara4k.tutor18.view.custom.ItemView;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class EventsActivity extends BaseActivity implements EventsIF {

    @BindView(R.id.start_date_item_view)
    ItemView mStartItemView;
    @BindView(R.id.end_date_item_view)
    ItemView mEndItemView;
    @BindView(R.id.create_date_events_button)
    Button mCreateDateBtn;
    @BindView(R.id.delete_date_events_button)
    Button mDeleteDateBtn;

    @Inject
    EventsPresenter mPresenter;
    private Calendar mCalendar = Calendar.getInstance();
    private Calendar mStartCal = Calendar.getInstance();
    private Calendar mEndCal = Calendar.getInstance();

    @Override
    protected int getContentView() {
        return R.layout.activity_events;
    }

    @Override
    protected void injectDaggerDependencies() {
        DaggerViewComponent.builder()
                .appComponent(getAppComponent())
                .viewModule(new ViewModule(this))
                .build().injectEventsActivity(this);
    }

    @Override
    protected void onViewReady() {
        mStartItemView.setSummary(FormatUtils.formatCalDay(mStartCal));
        mEndItemView.setSummary(FormatUtils.formatCalDay(mEndCal));
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @OnClick(R.id.start_date_item_view)
    void onStartDateClick() {
        DatePickerDialog.OnDateSetListener listener = (datePicker, i, i1, i2) -> {
            mStartCal.set(i, i1, i2);
            mStartItemView.setSummary(FormatUtils.formatCalDay(mStartCal));
        };

        showDatePicker(listener);
    }

    @OnClick(R.id.end_date_item_view)
    void onEndDateClick() {
        DatePickerDialog.OnDateSetListener listener = (datePicker, i, i1, i2) -> {
            mEndCal.set(i, i1, i2);
            mEndItemView.setSummary(FormatUtils.formatCalDay(mEndCal));
        };

        showDatePicker(listener);
    }

    private void showDatePicker(DatePickerDialog.OnDateSetListener listener) {
        new DatePickerDialog(this, R.style.PickerStyle, listener,
                getYear(), getMonth(), getDay())
                .show();
    }

    @OnClick(R.id.create_date_events_button)
    void onCreateEventsClick() {
        String title = getString(R.string.dialog_fill_schedule_title);
        String message = getString(R.string.dialog_fill_schedule_message);
        DialogInterface.OnClickListener okListener = (dialogInterface, i) -> {
            mPresenter.onCreateEventsClick(mStartCal, mEndCal);
        };

        showConfirmDialog(title, message, okListener);
    }

    @OnClick(R.id.delete_date_events_button)
    void onDeleteEventsClick() {
        String title = getString(R.string.dialog_delete_schedule_title);
        String message = getString(R.string.dialog_delete_schedule_message);
        DialogInterface.OnClickListener okListener = (dialogInterface, i) -> {
            mPresenter.onDeleteEventsClick(mStartCal, mEndCal);
        };

        showConfirmDialog(title, message, okListener);
    }

    private int getYear() {
        return mCalendar.get(Calendar.YEAR);
    }

    private int getMonth() {
        return mCalendar.get(Calendar.MONTH);
    }

    private int getDay() {
        return mCalendar.get(Calendar.DAY_OF_MONTH);
    }


    public static Intent newIntent(Context context) {
        return new Intent(context, EventsActivity.class);
    }
}
