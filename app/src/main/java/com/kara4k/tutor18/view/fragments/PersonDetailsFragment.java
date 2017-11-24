package com.kara4k.tutor18.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.model.Person;
import com.kara4k.tutor18.view.custom.ItemView;

import butterknife.BindView;

public class PersonDetailsFragment extends BaseFragment {

    public static final String PERSON = "person";

    @BindView(R.id.name_item_view)
    ItemView mNameItemView;
    @BindView(R.id.grade_item_view)
    ItemView mGradeItemView;
    @BindView(R.id.phone_item_view)
    ItemView mPhoneItemView;
    @BindView(R.id.note_item_view)
    ItemView mNoteItemView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_person_details;
    }

    @Override
    protected int getMenuRes() {
        return R.menu.fragment_person_details;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Person person = (Person) getArguments().getSerializable(PERSON);

        if (person == null) return;

        mNameItemView.setTitle(person.getFirstName());
        mNameItemView.setSummary(person.getName());
        mGradeItemView.setSummary(String.valueOf(person.getGrade()));
        mPhoneItemView.setSummary(person.getPhone());
        mNoteItemView.setSummary(person.getNote());
    }

    public static PersonDetailsFragment newInstance(Person person) {
        Bundle args = new Bundle();
        args.putSerializable(PERSON, person);
        PersonDetailsFragment fragment = new PersonDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
