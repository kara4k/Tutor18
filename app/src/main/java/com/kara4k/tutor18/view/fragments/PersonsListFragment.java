package com.kara4k.tutor18.view.fragments;


import android.content.Intent;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.di.DaggerViewComponent;
import com.kara4k.tutor18.di.modules.ViewModule;
import com.kara4k.tutor18.model.Person;
import com.kara4k.tutor18.presenter.PersonsListPresenter;
import com.kara4k.tutor18.view.PersonsListViewIF;
import com.kara4k.tutor18.view.activities.PersonActivity;
import com.kara4k.tutor18.view.adapters.Adapter;
import com.kara4k.tutor18.view.adapters.PersonsAdapter;

import javax.inject.Inject;

import butterknife.OnClick;

public class PersonsListFragment extends BaseListFragment<Person> implements PersonsListViewIF {

    @Inject
    PersonsListPresenter mPresenter;

    @Override
    protected Adapter getAdapter() {
        return new PersonsAdapter(mPresenter);
    }

    @Override
    protected void injectDaggerDependencies() {
        DaggerViewComponent.builder()
                .appComponent(getAppComponent())
                .viewModule(new ViewModule(this))
                .build().injectPersonListFrag(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @OnClick(R.id.fab)
    void onFabClicked() {
        startActivity(PersonActivity.newIntent(getContext()));
    }

    @Override
    public void showDetails(Person person) {
        long id = person.getId();
        int mode = PersonActivity.MODE_SHOW;
        Intent intent = PersonActivity.newIntent(getContext(), id, mode);

        startActivity(intent);
    }

    public static PersonsListFragment newInstance() {
        return new PersonsListFragment();
    }
}
