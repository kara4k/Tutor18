package com.kara4k.tutor18.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    public static final String TIMESTAMP = "timestamp";

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
    protected void onViewReady() {
        super.onViewReady();
        mFab.setVisibility(View.GONE);
        if (getArguments()!= null) {
            long timeStamp = getArguments().getLong(TIMESTAMP);
            mCalendar.setTimeInMillis(timeStamp);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart(mCalendar);
    }

    @Override
    public void showDetails(Event event) {
        Intent intent = EventDetails.newIntent(getContext(), event.getId());
        startActivity(intent);
    }

    public static EventsDayFragment newInstance(long timeStamp) {
        Bundle args = new Bundle();
        args.putLong(TIMESTAMP, timeStamp);
        EventsDayFragment fragment = new EventsDayFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
