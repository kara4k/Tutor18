package com.kara4k.tutor18.presenter;


import android.util.Log;

import com.kara4k.tutor18.model.DaoSession;
import com.kara4k.tutor18.model.Lesson;
import com.kara4k.tutor18.model.LessonDao;
import com.kara4k.tutor18.model.Person;
import com.kara4k.tutor18.model.PersonDao;
import com.kara4k.tutor18.view.PersonViewIF;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PersonPresenter implements Presenter, CompletableObserver {

    @Inject
    PersonViewIF mView;

    private PersonDao mPersonDao;
    private LessonDao mLessonDao;
    private CompositeDisposable mCompositeDisposable;

    @Inject
    public PersonPresenter(DaoSession daoSession) {
        mPersonDao = daoSession.getPersonDao();
        mLessonDao = daoSession.getLessonDao();
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
        Completable completable = Completable.fromAction(()
                -> mPersonDao.delete(person));

        subscribe(completable);
        mView.closeView();
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

    private Person queryPerson(long id) {
        return mPersonDao.queryBuilder()
                .where(PersonDao.Properties.Id.eq(id))
                .build().unique();
    }

    @Override
    public void onSubscribe(Disposable d) {
        mCompositeDisposable.add(d);
    }

    @Override
    public void onComplete() {
        Log.e("PersonPresenter", "onComplete: " + "done");
    }

    @Override
    public void onError(Throwable e) {
        mView.showError(e.getMessage());
    }


    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
    }
}
