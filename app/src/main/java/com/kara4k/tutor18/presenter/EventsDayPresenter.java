package com.kara4k.tutor18.presenter;


import com.kara4k.tutor18.model.DaoSession;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.view.EventsDayIF;

import javax.inject.Inject;

public class EventsDayPresenter extends ListPresenter<Event, EventsDayIF> {

    @Inject
    public EventsDayPresenter(DaoSession daoSession) {
    }

    @Override
    public void onItemClicked(Event event) {

    }
}
