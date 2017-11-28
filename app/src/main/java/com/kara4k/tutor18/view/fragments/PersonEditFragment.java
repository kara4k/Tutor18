package com.kara4k.tutor18.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.model.Person;
import com.kara4k.tutor18.view.activities.PersonActivity;

import butterknife.BindView;

public class PersonEditFragment extends BaseFragment {

    public static final String PERSON = "person";

    @BindView(R.id.first_name_edit_text)
    EditText mFirstNameEditText;
    @BindView(R.id.name_edit_text)
    EditText mNameEditText;
    @BindView(R.id.grade_edit_text)
    EditText mGradeEditText;
    @BindView(R.id.phone_edit_text)
    EditText mPhoneEditText;
    @BindView(R.id.note_edit_text)
    EditText mNoteEditText;

    private Person mPerson;

    @Override
    protected int getLayout() {
        return R.layout.fragment_person_edit;
    }

    @Override
    protected int getMenuRes() {
        return R.menu.fragment_person_edit;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) return;
        mPerson = (Person) getArguments().getSerializable(PERSON);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPerson == null) return;

        mFirstNameEditText.setText(mPerson.getFirstName());
        mNameEditText.setText(mPerson.getName());
        mGradeEditText.setText(mPerson.getGrade());
        mPhoneEditText.setText(mPerson.getPhone());
        mNoteEditText.setText(mPerson.getNote());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_save:
                if (mPerson == null) mPerson = new Person();

                mPerson.setFirstName(mFirstNameEditText.getText().toString());
                mPerson.setName(mNameEditText.getText().toString());
                mPerson.setGrade(mGradeEditText.getText().toString());
                mPerson.setPhone(mPhoneEditText.getText().toString());
                mPerson.setNote(mNoteEditText.getText().toString());

                ((PersonActivity) getActivity()).onSavePerson(mPerson);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static PersonEditFragment newInstance(Person person) {
        Bundle args = new Bundle();
        args.putSerializable(PERSON, person);
        PersonEditFragment fragment = new PersonEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static PersonEditFragment newInstance() {
        return new PersonEditFragment();
    }
}
