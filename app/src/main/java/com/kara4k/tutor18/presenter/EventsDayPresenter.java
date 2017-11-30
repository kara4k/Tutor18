package com.kara4k.tutor18.presenter;


import com.kara4k.tutor18.model.DaoSession;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.model.Lesson;
import com.kara4k.tutor18.model.LessonDao;
import com.kara4k.tutor18.model.Person;
import com.kara4k.tutor18.model.PersonDao;
import com.kara4k.tutor18.other.FormatUtils;
import com.kara4k.tutor18.view.EventsDayIF;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class EventsDayPresenter extends ListPresenter<Event, EventsDayIF> {

    private LessonDao mLessonDao;
    private PersonDao mPersonDao;

    @Inject
    public EventsDayPresenter(DaoSession daoSession) {
        mLessonDao = daoSession.getLessonDao();
        mPersonDao = daoSession.getPersonDao();
    }

    public void onStart(Calendar calendar) {
        int weekDay = FormatUtils.getDayDbValue(calendar.get(Calendar.DAY_OF_WEEK));
        List<Lesson> lessons = queryLessons(weekDay);
        for (Lesson lesson : lessons) {
            Person person = mPersonDao.queryBuilder().where(PersonDao.Properties.Id.eq(lesson.getPersonId())).build().unique();
//            Event event = new Event();
//            event.setPersonId(person.getId());
//            event.setDate();
//            event.
        }
        List<Event> list = new ArrayList<>();
        list.add(new Event());
        list.add(new Event());
        list.add(new Event());
        list.add(new Event());
        list.add(new Event());
        getView().setItems(list);
    }

    private List<Lesson> queryLessons(int weekDay) {
        return mLessonDao.queryBuilder()
                .where(LessonDao.Properties.DayOfWeek.eq(weekDay))
                .build().list();
    }

    @Override
    public void onItemClicked(Event event) {

    }
}
