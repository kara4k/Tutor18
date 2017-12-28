package com.kara4k.tutor18.di.modules;


import com.kara4k.tutor18.di.scopes.PerActivity;
import com.kara4k.tutor18.view.EventDetailsIF;
import com.kara4k.tutor18.view.EventsDayIF;
import com.kara4k.tutor18.view.SchedulerIF;
import com.kara4k.tutor18.view.EventsWeekIF;
import com.kara4k.tutor18.view.PersonViewIF;
import com.kara4k.tutor18.view.PersonsListViewIF;
import com.kara4k.tutor18.view.StatsIF;
import com.kara4k.tutor18.view.ViewIF;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModule {

    private ViewIF mViewIF;

    public ViewModule(ViewIF viewIF) {
        mViewIF = viewIF;
    }

    @Provides
    @PerActivity
    PersonsListViewIF providePersonsListView() {
        return (PersonsListViewIF) mViewIF;
    }

    @Provides
    @PerActivity
    PersonViewIF providePersonView() {
        return (PersonViewIF) mViewIF;
    }

    @Provides
    @PerActivity
    EventsDayIF provideEventsDayView() {
        return (EventsDayIF) mViewIF;
    }

    @Provides
    @PerActivity
    SchedulerIF provideEventsView() {
        return (SchedulerIF) mViewIF;
    }

    @Provides
    @PerActivity
    EventDetailsIF provideEventDetailsView() {
        return (EventDetailsIF) mViewIF;
    }

    @Provides
    @PerActivity
    EventsWeekIF provideEventsWeekView() {
        return (EventsWeekIF) mViewIF;
    }

    @Provides
    @PerActivity
    StatsIF provideStatsView() {
        return (StatsIF) mViewIF;
    }
}
