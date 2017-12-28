package com.kara4k.tutor18.presenter;


import com.kara4k.tutor18.model.DaoSession;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.model.EventDao;
import com.kara4k.tutor18.other.CalendarUtils;
import com.kara4k.tutor18.other.FormatUtils;
import com.kara4k.tutor18.presenter.vo.Stat;
import com.kara4k.tutor18.view.StatsIF;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StatsPresenter implements Presenter {

    @Inject
    StatsIF mView;
    EventDao mEventDao;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Inject
    public StatsPresenter(DaoSession daoSession) {
        mEventDao = daoSession.getEventDao();
    }

    public void onStart(long time) {
        Disposable disposable = Completable.fromAction(() -> calcStats(time))
                .subscribeOn(Schedulers.newThread())
                .subscribe(() -> {
                }, this::showError);

        mCompositeDisposable.add(disposable);

    }

    private void calcStats(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        CalendarUtils.setZeroTime(calendar);
        long dayStart = calendar.getTimeInMillis();
        long dayEnd = CalendarUtils.getDayEnd(calendar);
        updateStat(dayStart, dayEnd, Stat.TYPE_DAY);

        int dayDbValue = FormatUtils.getDayDbValue(calendar.get(Calendar.DAY_OF_WEEK));
        calendar.add(Calendar.DAY_OF_MONTH, -1 * dayDbValue);
        long weekStart = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        long weekEnd = calendar.getTimeInMillis();
        updateStat(weekStart, weekEnd, Stat.TYPE_WEEK);

        calendar.setTimeInMillis(dayStart);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        long monthStart = calendar.getTimeInMillis();
        int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        long monthEnd = calendar.getTimeInMillis();
        updateStat(monthStart, monthEnd, Stat.TYPE_MONTH);
    }

    private void updateStat(long start, long end, int type) {
        Stat stat = new Stat();
        stat.setType(type);

        Observable.fromCallable(() -> getEvents(start, end))
                .flatMapIterable(events -> events)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Event>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Event event) {
                        int state = event.getState();
                        if (state == Event.NOT_HELD) {
                            stat.addCanceled();
                        } else if (state == Event.HELD) {
                            stat.addCompleted();
                            stat.addOwned(event.getLesson().getPrice());
                            if (event.isPaid()) {
                                stat.addPayment(event.getPrice());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError(e);
                    }

                    @Override
                    public void onComplete() {
                        mView.setStats(stat);
                    }
                });
    }

    private List<Event> getEvents(long start, long end) {
        List<Event> events = mEventDao.queryBuilder()
                .where(EventDao.Properties.Id.between(start, end))
                .build().list();

        if (events == null) {
            return new ArrayList<>();
        }

        return events;
    }

    private void showError(Throwable throwable) {
        mView.showError(throwable.getMessage());
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
    }
}
