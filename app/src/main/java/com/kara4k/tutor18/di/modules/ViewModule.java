package com.kara4k.tutor18.di.modules;


import com.kara4k.tutor18.di.scopes.PerActivity;
import com.kara4k.tutor18.view.PersonViewIF;
import com.kara4k.tutor18.view.PersonsListViewIF;
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
}
