package com.kara4k.tutor18.view.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.model.WeekEvent;

public class EventsWeekAdapter extends Adapter<WeekEvent, EventsWeekAdapter.DayHolder> {

    @Override
    public DayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_week_events, parent, false);
        return new DayHolder(view);
    }

    class DayHolder extends Holder<WeekEvent> {

        public DayHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
