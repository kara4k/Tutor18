package com.kara4k.tutor18.view.fragments;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.model.Person;
import com.kara4k.tutor18.view.activities.PersonActivity;
import com.kara4k.tutor18.view.custom.ItemView;

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
    @BindView(R.id.add_lesson_item_view)
    ItemView mAddLessonItemView;
    @BindView(R.id.layout)
    LinearLayout mLessonsLayout;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_save:
                if (mPerson == null) mPerson = new Person();

                mPerson.setFirstName(mFirstNameEditText.getText().toString());
                mPerson.setName(mNameEditText.getText().toString());
                mPerson.setGrade(Integer.parseInt(mGradeEditText.getText().toString()));
                mPerson.setPhone(mPhoneEditText.getText().toString());
                mPerson.setNote(mNoteEditText.getText().toString());

                getPersonActivity().onSavePressed(mPerson);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private PersonActivity getPersonActivity() {
        return (PersonActivity) getActivity();
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
