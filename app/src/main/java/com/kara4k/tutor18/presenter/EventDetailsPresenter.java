package com.kara4k.tutor18.presenter;


import android.content.Context;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.model.DaoSession;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.model.EventDao;
import com.kara4k.tutor18.view.EventDetailsIF;

import javax.inject.Inject;

public class EventDetailsPresenter implements Presenter {

    @Inject
    EventDetailsIF mView;
    @Inject
    Context mContext;
    private EventDao mEventDao;

    @Inject
    public EventDetailsPresenter(DaoSession daoSession) {
        mEventDao = daoSession.getEventDao();
    }

    public void onStart(long eventId) {
        Event event = mEventDao.queryBuilder()
                .where(EventDao.Properties.Id.eq(eventId))
                .build().unique();

        if (event == null) { // TODO: 10.12.2017 filter
            mView.showError("Smth go wrong");
        } else {
            mView.showDetails(event);
        }
    }

    public void onSaveEvent(Event event) {
        if (event.getRescheduledToId() != null) {
            Event rescheduledEvent = createRescheduledEvent(event);
            mEventDao.insertOrReplace(rescheduledEvent);
        }
        mEventDao.insertOrReplace(event);

        String message = mContext.getString(R.string.message_saved);
        mView.showMessage(message);
    }

    private Event createRescheduledEvent(Event event) {
        Event reschEvent = new Event();
        reschEvent.setId(event.getRescheduledToId());
        reschEvent.setRescheduledFromId(event.getId());
        reschEvent.setLesson(event.getLesson());
        reschEvent.setPerson(event.getPerson());
        reschEvent.setSubjects(event.getSubjects());
        reschEvent.setNote(event.getNote());
        reschEvent.setIsPayment(event.isPayment());
        reschEvent.setIsPaid(event.isPaid());
        reschEvent.setExpectedPrice(event.getExpectedPrice());
        reschEvent.setState(Event.UNDEFINED);
        return reschEvent;
    }

    public void onDeleteEvent(Event event) {
        mEventDao.delete(event);
        mEventDao.detach(event);
        mView.closeView();
    }

    @Override
    public void onDestroy() {

    }
}
