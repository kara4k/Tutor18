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

        Calendar calendar = Calendar.getInstance();
        for (Event event : events) {

            List<Event> payments = mEventDao.queryBuilder()
                    .where(EventDao.Properties.Id.lt(event.getId()),
                            EventDao.Properties.PersonId.eq(event.getPersonId()),
                            EventDao.Properties.IsPaid.eq(true))
                    .orderDesc(EventDao.Properties.Id)
                    .build().list();

            Event lastPayment = null;
            if (!payments.isEmpty()) lastPayment = payments.get(0);

            if (lastPayment == null) {
                List<Event> personEvents = mEventDao.queryBuilder()
                        .where(EventDao.Properties.Id.le(event.getId()),
                                EventDao.Properties.PersonId.eq(event.getPersonId()))
                        .orderAsc(EventDao.Properties.Id).build().list();

                Event firstEvent = null;
                if (!personEvents.isEmpty()) firstEvent = personEvents.get(0);

                if (firstEvent == null) {
                    event.setPrice(0);
                } else {
                    double price = mPriceCalculator.calculatePrice(event, firstEvent.getId(), endStamp);
                    event.setPrice(price);
                }

            } else {
                calendar.setTimeInMillis(lastPayment.getId());
                CalendarUtils.setZeroTime(calendar);
                long startCount = CalendarUtils.getDayEnd(calendar);

                double price = mPriceCalculator.calculatePrice(event, startCount, endStamp);
                event.setPrice(price);
            }

        }

        return events;
    }

    @Override
    public void onItemClicked(Event event) {
        getView().showDetails(event);
    }
}
