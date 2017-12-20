package com.kara4k.tutor18.presenter;


import com.kara4k.tutor18.model.DaoSession;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.model.EventDao;
import com.kara4k.tutor18.model.Lesson;
import com.kara4k.tutor18.model.LessonDao;
import com.kara4k.tutor18.model.Person;
import com.kara4k.tutor18.model.PersonDao;
import com.kara4k.tutor18.view.PersonViewIF;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class PersonPresenter implements Presenter, CompletableObserver {

    @Inject
    PersonViewIF mView;

    private PersonDao mPersonDao;
    private LessonDao mLessonDao;
    private EventDao mEventDao;
    private CompositeDisposable mCompositeDisposable;

    @Inject
    public PersonPresenter(DaoSession daoSession) {
        mPersonDao = daoSession.getPersonDao();
        mLessonDao = daoSession.getLessonDao();
        mEventDao = daoSession.getEventDao();
        mCompositeDisposable = new CompositeDisposable();
    }

    public void onCreateNewPerson() {
        mView.showPersonCreator();
    }

    public void onShowPersonDetails(long id) {
        mView.showPersonDetails(queryPerson(id));
    }

    public void onEditPerson(long id) {
        mView.showPersonEdits(queryPerson(id));
    }

    public void onSavePerson(Person person) {
        Completable completable = Completable.fromCallable(()
                -> mPersonDao.insertOrReplace(person));

        subscribe(completable);
        mView.showPersonDetails(person);
    }

    public void onSavePerson(Lesson lesson, Person person) {
        Completable completable = Completable.fromAction(() -> {
            lesson.setPersonId(person.getId());
            mLessonDao.insertOrReplace(lesson);
            mPersonDao.insertOrReplace(person);
        });

        subscribe(completable);
        mView.showPersonDetails(person);
    }

    public void onDeletePerson(Person person) {
        Completable completable = Completable.fromAction(() -> {
            mPersonDao.delete(person);
            mLessonDao.deleteInTx(queryLessons(person.getId()));
            mEventDao.deleteInTx(queryEvents(person.getId()));
        });

        subscribe(completable, () -> mView.closeView());
    }

    public void onDeleteLesson(long id) {
        Completable completable = Completable.fromAction(()
                -> mLessonDao.deleteByKey(id));

        subscribe(completable);
    }

    private void subscribe(Completable completable) {
        completable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    private void subscribe(Completable completable, Action onComplete) {
        completable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onComplete, this::onError);
    }

    private Person queryPerson(long id) {
        return mPersonDao.queryBuilder()
                .where(PersonDao.Properties.Id.eq(id))
                .build().unique();
    }

    private List<Lesson> queryLessons(long personId) {
        return mLessonDao.queryBuilder()
                .where(LessonDao.Properties.PersonId.eq(personId))
                .build().list();
    }

    private List<Event> queryEvents(long personId) {
        return mEventDao.queryBuilder()
                .where(LessonDao.Properties.PersonId.eq(personId))
                .build().list();
    }

    @Override
    public void onSubscribe(Disposable d) {
        mCompositeDisposable.add(d);
    }

    @Override
    public void onComplete() {}

    @Override
    public void onError(Throwable e) {
        mView.showError(e.getMessage());
    }


    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
    }
}
