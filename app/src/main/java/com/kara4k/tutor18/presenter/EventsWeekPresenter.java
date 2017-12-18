package com.kara4k.tutor18.presenter;


import com.kara4k.tutor18.model.DaoSession;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.model.EventDao;
import com.kara4k.tutor18.model.WeekEvent;
import com.kara4k.tutor18.other.CalendarUtils;
import com.kara4k.tutor18.other.FormatUtils;
import com.kara4k.tutor18.view.EventsWeekIF;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class EventsWeekPresenter extends ListPresenter<WeekEvent,EventsWeekIF> {

    private final static long DAY_IN_MILLIS = 24 * 60 * 60 * 1000;

    @Inject
    EventsWeekIF mView;
    private EventDao mEventDao;

    @Inject
    public EventsWeekPresenter(DaoSession daoSession) {
        mEventDao = daoSession.getEventDao();
    }

    public void onStart(Calendar calendar) {
        int dayDbValue = FormatUtils.getDayDbValue(calendar.get(Calendar.DAY_OF_WEEK));
        CalendarUtils.setZeroTime(calendar);
        calendar.add(Calendar.DAY_OF_MONTH , -1 * dayDbValue);
        long startStamp = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        long endStamp = calendar.getTimeInMillis();

        List<Event> events = mEventDao.queryBuilder()
                .where(EventDao.Properties.Id.between(startStamp, endStamp))
                .build().list();

        List<WeekEvent> weekEvents = createEmtyWeekEvents();
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            Long eventId = event.getId();
            long stamp = eventId - startStamp;
            int index = (int) (stamp / DAY_IN_MILLIS);
            weekEvents.get(index).addEvent(event);
        }

        mView.setItems(weekEvents);

    }

    private List<WeekEvent> createEmtyWeekEvents() {
        List<WeekEvent> events = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            events.add(new WeekEvent());
        }
        return events;
    }

    @Override
    public void onItemClicked(WeekEvent weekEvent) {

    }

    @Override
    public void onDestroy() {

    }
}
