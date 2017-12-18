package com.kara4k.tutor18.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kara4k.tutor18.di.DaggerViewComponent;
import com.kara4k.tutor18.di.modules.ViewModule;
import com.kara4k.tutor18.model.WeekEvent;
import com.kara4k.tutor18.presenter.EventsWeekPresenter;
import com.kara4k.tutor18.view.EventsWeekIF;
import com.kara4k.tutor18.view.adapters.Adapter;
import com.kara4k.tutor18.view.adapters.EventsWeekAdapter;

import java.util.Calendar;

import javax.inject.Inject;

public class EventsWeekFragment extends BaseListFragment<WeekEvent> implements EventsWeekIF {

    public static final String TIMESTAMP = "timestamp";

    @Inject
    EventsWeekPresenter mPresenter;
    Calendar mCalendar = Calendar.getInstance();

    @Override
    protected Adapter getAdapter() {
        return new EventsWeekAdapter();
    }

    @NonNull
    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 2);
    }

    @Override
    protected void injectDaggerDependencies() {
        DaggerViewComponent.builder()
                .appComponent(getAppComponent())
                .viewModule(new ViewModule(this))
                .build().injectEventsWeekFrag(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFab.setVisibility(View.GONE);
        if (getArguments()!= null) {
            long timeStamp = getArguments().getLong(TIMESTAMP);
            mCalendar.setTimeInMillis(timeStamp);
        }

        mPresenter.onStart(mCalendar);

    }



    public static EventsWeekFragment newInstance(long timeStamp) {
        Bundle args = new Bundle();
        args.putLong(TIMESTAMP, timeStamp);
        EventsWeekFragment fragment = new EventsWeekFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
