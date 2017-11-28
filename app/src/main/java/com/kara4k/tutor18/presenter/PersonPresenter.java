package com.kara4k.tutor18.presenter;


import android.util.Log;

import com.kara4k.tutor18.model.DaoSession;
import com.kara4k.tutor18.model.Lesson;
import com.kara4k.tutor18.model.LessonDao;
import com.kara4k.tutor18.model.Person;
import com.kara4k.tutor18.model.PersonDao;
import com.kara4k.tutor18.view.PersonViewIF;

import javax.inject.Inject;

public class PersonPresenter implements PresenterIF {

    @Inject
    PersonViewIF mView;

    private PersonDao mPersonDao;
    private LessonDao mLessonDao;

    @Inject
    public PersonPresenter(DaoSession daoSession) {
        mPersonDao = daoSession.getPersonDao();
        mLessonDao = daoSession.getLessonDao();
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
        mPersonDao.insertOrReplace(person); // TODO: 24.11.2017 rx
        mView.showPersonDetails(person);
    }

    public void onSavePerson(Lesson lesson, Person person) {
        lesson.setPersonId(person.getId());
        mLessonDao.insertOrReplace(lesson);
        mPersonDao.insertOrReplace(person);
        mView.showPersonDetails(person);
    }

    public void onDeletePerson(Person person) {
        mPersonDao.delete(person);
        mView.closeView();
    }

    private Person queryPerson(long id) {
        return mPersonDao.queryBuilder()
                .where(PersonDao.Properties.Id.eq(id))
                .build().unique();
    }

    public void onDeleteLesson(long id) {
        Log.e("PersonPresenter", "onDeleteLesson: " + id);
        mLessonDao.deleteByKey(id);
    }

    @Override
    public void onDestroy() {

    }
}
