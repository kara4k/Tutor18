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

    @Inject
    PriceCalculator mPriceCalculator;
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
        return getEvents(startStamp, endStamp);
    }

    private List<Event> getEvents(long startStamp, long endStamp) {
        List<Event> events = mEventDao.queryBuilder()
                .where(EventDao.Properties.Id.between(startStamp, endStamp))
                .build().list();

        for (Event event : events) {
            if (event.isPayment()) {
                setEventPrice(startStamp, endStamp, event);
            }
        }

        return events;
    }

    private void setEventPrice(long startStamp, long endStamp, Event event) {
        long monthStart;
        double totalPrice;

        if (event.getRescheduledFromId() == null) {
            monthStart = CalendarUtils.getMonthStart(startStamp);
        } else {
            // TODO: 17.12.2017  recursive check
            Event unique = mEventDao.queryBuilder().where(EventDao.Properties.Id.eq(event.getRescheduledFromId())).build().unique();
            if (!unique.getIsPaid() && unique.getRescheduledFromId() == null) {

            }

            monthStart = CalendarUtils.getMonthStart(event.getRescheduledFromId());
        }

        totalPrice = mPriceCalculator.calculatePrice(event, monthStart, endStamp);
        event.setPrice(totalPrice);
    }

    @Override
    public void onItemClicked(Event event) {
        getView().showDetails(event);
    }
}
