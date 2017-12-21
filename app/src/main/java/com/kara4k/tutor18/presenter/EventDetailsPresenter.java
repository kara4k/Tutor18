package com.kara4k.tutor18.presenter;


import android.content.Context;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.model.DaoSession;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.model.EventDao;
import com.kara4k.tutor18.view.EventDetailsIF;

import java.math.BigDecimal;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EventDetailsPresenter implements Presenter {

    @Inject
    EventDetailsIF mView;
    @Inject
    Context mContext;
    private EventDao mEventDao;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private boolean included = false;

    @Inject
    public EventDetailsPresenter(DaoSession daoSession) {
        mEventDao = daoSession.getEventDao();
    }

    public void onStart(long eventId) {
        Consumer<Event> onSuccess = (event) -> {
            initPaymentVars(event);
            mView.showDetails(event);
        };

        Disposable disposable = Single.fromCallable(() -> getEvent(eventId))
                .filter(event -> event != null)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, this::onError);

        mCompositeDisposable.add(disposable);
    }

    private void initPaymentVars(Event event) {
        if (event.getState() == Event.HELD) {
            included = true;
        }
    }

    private Event getEvent(long eventId) {
        return mEventDao.queryBuilder()
                .where(EventDao.Properties.Id.eq(eventId))
                .build().unique();
    }

    /**
     * Calculate total payment on event state change, depends on include or not last lesson
     * @param event current event
     */
    public void onEventStateChange(Event event) {
            BigDecimal price = new BigDecimal(event.getPrice());
            BigDecimal lessonPrice = new BigDecimal(event.getLesson().getPrice());
            BigDecimal total;

            if (event.getState() == Event.HELD && !included) {
                included = true;
                total = price.add(lessonPrice);
                event.setPrice(total.doubleValue());
            } else if (event.getState() != Event.HELD && included) {
                included = false;
                total = price.subtract(lessonPrice);
                event.setPrice(total.doubleValue());
            }
        mView.onUpdateEvent();
    }

    public void onSaveEvent(Event event) {
        Completable completable = Completable.fromAction(() -> updateEventDb(event));
        Action onComplete = () -> {
            String message = mContext.getString(R.string.message_saved);
            mView.showMessage(message);
        };

        subscribe(completable, onComplete);
    }

    private void updateEventDb(Event event) {
        System.out.println(event.getPrice());
        if (event.getRescheduledToId() != null) {
            Event rescheduledEvent = createRescheduledEvent(event);
            mEventDao.insertOrReplace(rescheduledEvent);
        }
        mEventDao.insertOrReplace(event);
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
        reschEvent.setPrice(event.getPrice());
        reschEvent.setIsPaid(event.isPaid());
        reschEvent.setExpectedPrice(event.getExpectedPrice());
        reschEvent.setState(Event.UNDEFINED);
        return reschEvent;
    }

    public void onDeleteEvent(Event event) {
        Completable completable = Completable.fromAction(() -> deleteEvent(event));
        Action onComplete = () -> mView.closeView();

        subscribe(completable, onComplete);
    }

    private void deleteEvent(Event event) {
        mEventDao.delete(event);
        mEventDao.detach(event);
    }

    private void subscribe(Completable completable, Action onComplete) {
        Disposable disposable = completable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onComplete, this::onError);

        mCompositeDisposable.add(disposable);
    }

    private void onError(Throwable throwable) {
        mView.showError(throwable.getMessage());
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
    }
}
