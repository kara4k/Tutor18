package com.kara4k.tutor18.presenter;


import com.kara4k.tutor18.model.DaoSession;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.model.EventDao;
import com.kara4k.tutor18.view.EventDetailsIF;

import javax.inject.Inject;

public class EventDetailsPresenter implements Presenter {

    @Inject
    EventDetailsIF mView;
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

    @Override
    public void onDestroy() {

    }
}
