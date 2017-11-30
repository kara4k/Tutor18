package com.kara4k.tutor18.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kara4k.tutor18.di.DaggerViewComponent;
import com.kara4k.tutor18.di.modules.ViewModule;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.presenter.EventsDayPresenter;
import com.kara4k.tutor18.view.EventsDayIF;
import com.kara4k.tutor18.view.adapters.Adapter;
import com.kara4k.tutor18.view.adapters.EventsDayAdapter;

import java.util.Calendar;

import javax.inject.Inject;

public class EventsDayFragment extends BaseListFragment<Event> implements EventsDayIF {

    @Inject
    EventsDayPresenter mPresenter;

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
        mPresenter.onStart(Calendar.getInstance());
    }

    public static EventsDayFragment newInstance() {

        Bundle args = new Bundle();

        EventsDayFragment fragment = new EventsDayFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
