package com.kara4k.tutor18.view.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.presenter.EventsDayPresenter;

public class EventsDayAdapter extends Adapter<Event, EventsDayAdapter.EventHolder> {

    private EventsDayPresenter mPresenter;

    public EventsDayAdapter(EventsDayPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_day_event, parent, false);
        return new EventHolder(view);
    }

    class EventHolder extends Holder<Event> {
        public EventHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {
            mPresenter.onItemClicked(mItem);
        }
    }
}
