package com.kara4k.tutor18.di;

import com.kara4k.tutor18.di.modules.ViewModule;
import com.kara4k.tutor18.di.scopes.PerActivity;
import com.kara4k.tutor18.view.activities.PersonActivity;
import com.kara4k.tutor18.view.fragments.EventsDayFragment;
import com.kara4k.tutor18.view.fragments.PersonsListFragment;

import dagger.Component;

@PerActivity
@Component(modules = ViewModule.class, dependencies = AppComponent.class)
public interface ViewComponent {

    void injectPersonListFrag(PersonsListFragment fragment);

    void injectPersonActivity(PersonActivity activity);

    void injectEventsDayFrag(EventsDayFragment fragment);
}
