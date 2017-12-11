package com.kara4k.tutor18.presenter;


import com.kara4k.tutor18.model.DaoSession;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.model.EventDao;
import com.kara4k.tutor18.other.CalendarUtils;
import com.kara4k.tutor18.view.EventsDayIF;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class EventsDayPresenter extends ListPresenter<Event, EventsDayIF> {

    private EventDao mEventDao;

    @Inject
    public EventsDayPresenter(DaoSession daoSession) {
        mEventDao = daoSession.getEventDao();
    }

    public void onStart(Calendar calendar) {
        subscribe(() -> getDayEvents(calendar));
    }

    private List<Event> getDayEvents(Calendar calendar) {
        CalendarUtils.setZeroTime(calendar);

        long startStamp = calendar.getTimeInMillis();
        long endStamp = CalendarUtils.getDayEnd(calendar);

        mEventDao.detachAll();
        return mEventDao.queryBuilder()
                .where(EventDao.Properties.Id.between(startStamp, endStamp))
                .build().list();
    }

    @Override
    public void onItemClicked(Event event) {
        getView().showDetails(event);
    }
}
