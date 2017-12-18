package com.kara4k.tutor18.presenter;


import com.kara4k.tutor18.model.DaoSession;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.model.EventDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;

public class PriceCalculator {

    private EventDao mEventDao;

    @Inject
    public PriceCalculator(DaoSession daoSession) {
        mEventDao = daoSession.getEventDao();
    }

    public double calculatePrice(Event event, long startStamp, long endStamp) {
        List<Event> completedEvents = getCompletedEvents(startStamp, endStamp, event);

        BigDecimal total = new BigDecimal(BigInteger.ZERO);
        for (int i = 0; i < completedEvents.size(); i++) {
            double price = completedEvents.get(i).getLesson().getPrice();
            BigDecimal lessonPrice = new BigDecimal(price);
            total = total.add(lessonPrice);
        }
        return total.doubleValue();
    }

    private List<Event> getCompletedEvents(long startStamp, long endStamp, Event event) {
        Long personId = event.getPersonId();

        QueryBuilder<Event> queryBuilder = mEventDao.queryBuilder();
        queryBuilder.where(EventDao.Properties.Id.between(startStamp, endStamp),
                EventDao.Properties.State.eq(Event.HELD),
                EventDao.Properties.PersonId.eq(personId));

        return queryBuilder.build().list();
    }
}
