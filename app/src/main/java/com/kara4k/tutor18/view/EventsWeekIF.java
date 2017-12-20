package com.kara4k.tutor18.view;


import com.kara4k.tutor18.model.WeekEvent;

public interface EventsWeekIF extends ListViewIF<WeekEvent> {

    void showDayDetails(long timestamp);
}
