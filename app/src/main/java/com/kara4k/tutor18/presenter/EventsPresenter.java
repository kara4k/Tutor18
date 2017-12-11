package com.kara4k.tutor18.presenter;


import android.content.Context;
import android.util.LongSparseArray;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.model.DaoSession;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.model.EventDao;
import com.kara4k.tutor18.model.Lesson;
import com.kara4k.tutor18.model.Person;
import com.kara4k.tutor18.other.CalendarUtils;
import com.kara4k.tutor18.other.FormatUtils;
import com.kara4k.tutor18.view.EventsIF;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EventsPresenter implements Presenter {

    @Inject
    EventsIF mView;
    @Inject
    Context mContext;

    private DaoSession mDaoSession;
    private EventDao mEventDao;

    private List<Lesson> mLessons;
    private LongSparseArray<Person> mPersons;

    @Inject
    public EventsPresenter(DaoSession daoSession) {
        mDaoSession = daoSession;
        mEventDao = daoSession.getEventDao();

        initPersonsArray(daoSession);
        initLessonsList(daoSession);
    }

    private void initLessonsList(DaoSession daoSession) {
        mLessons = daoSession.getLessonDao().queryBuilder().build().list();
    }

    private void initPersonsArray(DaoSession daoSession) {
        List<Person> persons = daoSession.getPersonDao().queryBuilder().build().list();
        mPersons = new LongSparseArray<>(persons.size());
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            mPersons.put(person.getId(), person);
        }
    }

    public void onCreateEventsClick(Calendar startCal, Calendar endCal) {
        Single.fromCallable(() -> createEvents(startCal, endCal))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::insertInDb, this::onError);
    }

    private void insertInDb(List<Event> events) {
        Completable.fromAction(() -> mEventDao.insertOrReplaceInTx(events))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    mView.showMessage(mContext.getString(
                            R.string.message_events_created, events.size()));
                }, this::onError);

    }

    public void onDeleteEventsClick(Calendar startCal, Calendar endCal) {
        Single.fromCallable(() -> deleteEvents(startCal, endCal))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((i) -> {
                            mView.showMessage(mContext.getString(
                                    R.string.message_events_deleted, i));
                        },
                        this::onError);
    }

    private int deleteEvents(Calendar startCal, Calendar endCal) {
        setupCalendars(startCal, endCal);

        long startStamp = startCal.getTimeInMillis();
        long endStamp = CalendarUtils.getDayEnd(endCal);

        List<Event> events = mEventDao.queryBuilder()
                .where(EventDao.Properties.Id.between(startStamp, endStamp))
                .build().list();

        mEventDao.deleteInTx(events);
        return events.size();
    }

    private List<Event> createEvents(Calendar startCal, Calendar endCal) {
        setupCalendars(startCal, endCal);
        List<Event> events = new LinkedList<>();

        long startStamp = startCal.getTimeInMillis();
        long endStamp = endCal.getTimeInMillis();

        if (startStamp > endStamp) {
            throw new IllegalStateException(mContext.getString(R.string.illegal_days_order));
        } else {
            while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
                createDayEvents(startCal, events);
            }
        }
        startCal.setTimeInMillis(startStamp);
        return events;
    }

    private void createDayEvents(Calendar startCal, List<Event> events) {
        int dayDbValue = FormatUtils.getDayDbValue(startCal.get(Calendar.DAY_OF_WEEK));
        for (int i = 0; i < mLessons.size(); i++) {
            Lesson lesson = mLessons.get(i);

            if (lesson.getDayOfWeek() == dayDbValue) {
                Event event = new Event();
                event.setId(calcEventId(startCal, lesson));
                event.setPersonId(lesson.getPersonId());
                event.setLessonId(lesson.getId());
                event.setIsHeld(Event.UNDEFINED);
                setPayment(startCal, lesson, event);
                events.add(event);
            }
        }
        startCal.add(Calendar.DAY_OF_MONTH, 1);
    }

    private void setPayment(Calendar startCal, Lesson lesson, Event event) {
        int lastDay = startCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int lastMonthDayValue = getLastMonthDayValue(startCal, lastDay);
        int lastSevenDays = lastDay - 7;

        if (startCal.get(Calendar.DAY_OF_MONTH) >= lastSevenDays) {
            Person person = mPersons.get(lesson.getPersonId());
            List<Lesson> lessons = person.getLessons();

            NavigableSet<Integer> daySet = new TreeSet<>();
            for (int j = 0; j < lessons.size(); j++) {
                daySet.add(lessons.get(j).getDayOfWeek());
            }

            Integer floor = daySet.floor(lastMonthDayValue);
            if (floor != null) {
                if (floor == lesson.getDayOfWeek()) {
                    event.setIsPayment(true);
                    event.setExpectedPrice(computeExpectedPrice(startCal, lessons));
                } else {
                    event.setIsPayment(false);
                }
            } else {
                Integer floor1 = daySet.floor(6);     //last lesson day of week 6 = Sunday
                if (floor1 == lesson.getDayOfWeek()) {
                    event.setIsPayment(true);
                    event.setExpectedPrice(computeExpectedPrice(startCal, lessons));
                } else {
                    event.setIsPayment(false);
                }
            }
        } else {
            event.setIsPayment(false);
        }
    }

    private double computeExpectedPrice(Calendar calendar, List<Lesson> lessons) {
        BigDecimal total = new BigDecimal(BigInteger.ZERO);

        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = lessons.get(i);
            int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_WEEK_IN_MONTH);
            BigDecimal count = new BigDecimal(daysInMonth);
            BigDecimal lessonPrice = new BigDecimal(lesson.getPrice());
            BigDecimal sum = lessonPrice.multiply(count);
            total = total.add(sum);
        }
        return total.doubleValue();
    }

    private int getLastMonthDayValue(Calendar calendar, int lastDay) {
        int lastDayValue;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(Calendar.DAY_OF_MONTH, lastDay);
        lastDayValue = FormatUtils.getDayDbValue(calendar.get(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.DAY_OF_MONTH, currentDay);
        return lastDayValue;
    }

    private void setupCalendars(Calendar startCal, Calendar finishCal) {
        CalendarUtils.setZeroTime(startCal);
        CalendarUtils.setZeroTime(finishCal);
        startCal.setFirstDayOfWeek(Calendar.MONDAY);
    }

    /**
     * Calculate event id by timestamp of lesson begins
     *
     * @param calendar day of event, where seconds and milliseconds = 0
     * @param lesson   current lesson
     * @return event id
     */
    private long calcEventId(Calendar calendar, Lesson lesson) {
        long id;
        long startStamp = calendar.getTimeInMillis();

        calendar.set(Calendar.HOUR_OF_DAY, lesson.getStartHour());
        calendar.set(Calendar.MINUTE, lesson.getStartMin());
        id = calendar.getTimeInMillis();
        calendar.setTimeInMillis(startStamp);
        return id;
    }

    public void onError(Throwable e) {
        mView.showError(e.getMessage());
    }

    @Override
    public void onDestroy() {

    }
}
