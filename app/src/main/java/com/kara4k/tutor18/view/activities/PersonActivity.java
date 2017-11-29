package com.kara4k.tutor18.view.activities;


import android.content.Context;
import android.content.Intent;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.di.DaggerViewComponent;
import com.kara4k.tutor18.di.modules.ViewModule;
import com.kara4k.tutor18.model.Lesson;
import com.kara4k.tutor18.model.Person;
import com.kara4k.tutor18.presenter.PersonPresenter;
import com.kara4k.tutor18.view.PersonViewIF;
import com.kara4k.tutor18.view.fragments.PersonDetailsFragment;
import com.kara4k.tutor18.view.fragments.PersonEditFragment;

import javax.inject.Inject;

public class PersonActivity extends BaseActivity implements PersonViewIF {

    public static final String PERSON_ID = "person_id";
    public static final String MODE = "mode";
    public static final int MODE_SHOW = 1;
    public static final int MODE_NEW = 2;
    public static final int MODE_EDIT = 3;
    public static final int EMPTY = -1;

    @Inject
    PersonPresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.container_view;
    }

    @Override
    protected void injectDaggerDependencies() {
        DaggerViewComponent.builder()
                .appComponent(getAppComponent())
                .viewModule(new ViewModule(this))
                .build().injectPersonActivity(this);
    }

    @Override
    protected void onViewReady() {
        int mode = getIntent().getIntExtra(MODE, EMPTY);
        long id = getIntent().getLongExtra(PERSON_ID, EMPTY);

        switch (mode) {
            case MODE_SHOW:
                if (id == EMPTY) return;
                mPresenter.onShowPersonDetails(id);
                break;
            case MODE_NEW:
                mPresenter.onCreateNewPerson();
                break;
            case MODE_EDIT:
                if (id == EMPTY) return;
                mPresenter.onEditPerson(id);
                break;
        }
    }

    @Override
    public void showPersonDetails(Person person) {
        hideSoftKeyboard();
        setFragment(PersonDetailsFragment.newInstance(person));
    }

    @Override
    public void showPersonEdits(Person person) {
        setFragment(PersonEditFragment.newInstance(person));
    }

    @Override
    public void showPersonCreator() {
        setFragment(PersonEditFragment.newInstance());
    }

    public void onSavePerson(Person person) {
        mPresenter.onSavePerson(person);
    }

    public void onEditPerson(Person person) {
        setFragment(PersonEditFragment.newInstance(person));
    }

    public void onDeletePerson(Person person) {
        mPresenter.onDeletePerson(person);
    }

    public void onDeleteLesson(long id) {
        mPresenter.onDeleteLesson(id);
    }

    @Override
    public void closeView() {
        finish();
    }

    public static Intent newIntent(Context context, Long personId, int mode) {
        Intent intent = new Intent(context, PersonActivity.class);
        intent.putExtra(MODE, mode);
        intent.putExtra(PERSON_ID, personId);
        return intent;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PersonActivity.class);
        intent.putExtra(MODE, MODE_NEW);
        return intent;
    }

    public void onSavePerson(Lesson lesson, Person person) {
        mPresenter.onSavePerson(lesson, person);
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }
}
