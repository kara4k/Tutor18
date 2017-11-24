package com.kara4k.tutor18.view.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.di.DaggerViewComponent;
import com.kara4k.tutor18.di.modules.ViewModule;
import com.kara4k.tutor18.model.Person;
import com.kara4k.tutor18.presenter.PersonsListPresenter;
import com.kara4k.tutor18.view.PersonsListViewIF;
import com.kara4k.tutor18.view.activities.PersonActivity;
import com.kara4k.tutor18.view.adapters.Adapter;
import com.kara4k.tutor18.view.adapters.PersonsAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.OnClick;

public class PersonsListFragment extends BaseListFragment implements PersonsListViewIF {

    public static final int NEW_PERSON = 1;
    public static final int SHOW_DETAILS = 2;

    @Inject
    PersonsListPresenter mPresenter;

    private PersonsAdapter mAdapter;

    @Override
    protected void injectDaggerDependencies() {
        DaggerViewComponent.builder()
                .appComponent(getAppComponent())
                .viewModule(new ViewModule(this))
                .build().injectPersonListFrag(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            mPresenter.onStart();
        }
    }

    @Override
    protected Adapter getAdapter() {
        return mAdapter = new PersonsAdapter(mPresenter);
    }

    @Override
    public void setItems(List<Person> persons) {
        mAdapter.setList(persons);
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @OnClick(R.id.fab)
    void onFabClicked() {
        startActivityForResult(PersonActivity.newIntent(getContext()), NEW_PERSON);
    }

    @Override
    public void showDetails(Person person) {
        long id = person.getId();
        int mode = PersonActivity.MODE_SHOW;
        Intent intent = PersonActivity.newIntent(getContext(), id, mode);

        startActivityForResult(intent, SHOW_DETAILS);
    }
}
