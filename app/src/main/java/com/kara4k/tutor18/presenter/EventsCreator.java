package com.kara4k.tutor18.presenter;


import com.kara4k.tutor18.model.DaoSession;
import com.kara4k.tutor18.model.EventDao;
import com.kara4k.tutor18.model.LessonDao;
import com.kara4k.tutor18.model.PersonDao;

import java.util.Calendar;

import javax.inject.Inject;

public class EventsCreator {

    private EventDao mEventDao;
    private PersonDao mPersonDao;
    private LessonDao mLessonDao;

    @Inject
    public EventsCreator(DaoSession daoSession) {
        mEventDao = daoSession.getEventDao();
        mPersonDao = daoSession.getPersonDao();
        mLessonDao = daoSession.getLessonDao();
    }

    public void createEvent(Calendar calendar) {

    }
}
