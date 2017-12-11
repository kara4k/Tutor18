package com.kara4k.tutor18.view.fragments;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.di.DaggerViewComponent;
import com.kara4k.tutor18.di.modules.ViewModule;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.presenter.EventsDayPresenter;
import com.kara4k.tutor18.view.EventsDayIF;
import com.kara4k.tutor18.view.activities.EventDetails;
import com.kara4k.tutor18.view.adapters.Adapter;
import com.kara4k.tutor18.view.adapters.EventsDayAdapter;

import java.util.Calendar;

import javax.inject.Inject;

public class EventsDayFragment extends BaseListFragment<Event> implements EventsDayIF {

    @Inject
    EventsDayPresenter mPresenter;
    private Calendar mCalendar = Calendar.getInstance();

    @Override
    protected Adapter getAdapter() {
        return new EventsDayAdapter(mPresenter);
    }

    @Override
    protected void injectDaggerDependencies() {
        DaggerViewComponent.builder()
                .appComponent(getAppComponent())
                .viewModule(new ViewModule(this))
                .build().injectEventsDayFrag(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFab.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart(mCalendar);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_day_events, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_select_date:
                DatePickerDialog.OnDateSetListener listener = (datePicker, i, i1, i2) -> {
                    mCalendar.set(i,i1,i2);
                    mPresenter.onStart(mCalendar);
                };

                showDatePicker(listener);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDatePicker(DatePickerDialog.OnDateSetListener listener) {
        new DatePickerDialog(getContext(), R.style.PickerStyle, listener,
                getYear(), getMonth(), getDay())
                .show();
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

    public static EventsDayFragment newInstance() {

        Bundle args = new Bundle();

        EventsDayFragment fragment = new EventsDayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showDetails(Event event) {
        Intent intent = EventDetails.newIntent(getContext(), event.getId());
        startActivity(intent);
    }
}
