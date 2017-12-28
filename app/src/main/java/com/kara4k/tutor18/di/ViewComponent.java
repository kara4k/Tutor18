package com.kara4k.tutor18.di;

import com.kara4k.tutor18.di.modules.ViewModule;
import com.kara4k.tutor18.di.scopes.PerActivity;
import com.kara4k.tutor18.view.activities.EventDetails;
import com.kara4k.tutor18.view.activities.SchedulerActivity;
import com.kara4k.tutor18.view.activities.PersonActivity;
import com.kara4k.tutor18.view.fragments.EventsDayFragment;
import com.kara4k.tutor18.view.fragments.EventsWeekFragment;
import com.kara4k.tutor18.view.fragments.PersonsListFragment;
import com.kara4k.tutor18.view.fragments.StatsFragment;

import dagger.Component;

@PerActivity
@Component(modules = ViewModule.class, dependencies = AppComponent.class)
public interface ViewComponent {

    void injectPersonListFrag(PersonsListFragment fragment);

    void injectPersonActivity(PersonActivity activity);

    void injectEventsActivity(SchedulerActivity activity);

    void injectEventsDayFrag(EventsDayFragment fragment);

    void injectEventDetailsActivity(EventDetails activity);

    void injectEventsWeekFrag(EventsWeekFragment fragment);

    void injectStatsFrag(StatsFragment fragment);
}
