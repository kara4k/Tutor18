package com.kara4k.tutor18.model;


import java.util.ArrayList;
import java.util.List;

public class WeekEvent {

    private int dayOfWeek;
    private int dayOfMonth;
    private int month;
    private int year;

    private List<Event> mEvents = new ArrayList<>();

    public List<Event> getEvents() {
        return mEvents;
    }

    public void setEvents(List<Event> events) {
        mEvents = events;
    }

    public void addEvent(Event event){
        mEvents.add(event);
    }
}
