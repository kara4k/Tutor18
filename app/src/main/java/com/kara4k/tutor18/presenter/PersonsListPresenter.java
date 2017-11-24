package com.kara4k.tutor18.presenter;


import com.kara4k.tutor18.model.DaoSession;
import com.kara4k.tutor18.model.Person;
import com.kara4k.tutor18.model.PersonDao;
import com.kara4k.tutor18.view.PersonsListViewIF;

import javax.inject.Inject;

public class PersonsListPresenter extends ListPresenter<Person, PersonsListViewIF> {

    private PersonDao mPersonDao;

    @Inject
    public PersonsListPresenter(DaoSession daoSession) {
        mPersonDao = daoSession.getPersonDao();
    }

    public void onStart() {
        showAllPersons();
    }

    private void showAllPersons() {
        subscribe(() -> mPersonDao.queryBuilder().build().list());
    }


    @Override
    public void onItemClicked(Person person) {
        getView().showDetails(person);
    }
}
