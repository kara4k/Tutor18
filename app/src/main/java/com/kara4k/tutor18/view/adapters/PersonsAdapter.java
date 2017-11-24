package com.kara4k.tutor18.view.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.model.Person;
import com.kara4k.tutor18.presenter.PersonsListPresenter;

import butterknife.BindView;

public class PersonsAdapter extends Adapter<Person, PersonsAdapter.PersonHolder> {

    PersonsListPresenter mPresenter;

    public PersonsAdapter(PersonsListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_person, parent, false);
        return new PersonHolder(view);
    }

    class PersonHolder extends Holder<Person> {

        @BindView(R.id.first_name_edit_text)
        TextView mFirstNameTextView;
        @BindView(R.id.name_edit_text)
        TextView mNameTextView;
        @BindView(R.id.grade_text_view)
        TextView mGradeTextView;
        @BindView(R.id.phone_text_view)
        TextView mPhoneTextView;
        @BindView(R.id.note_text_view)
        TextView mNoteTextView;

        public PersonHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(Person person) {
            super.onBind(person);
            mFirstNameTextView.setText(person.getFirstName());
            mNameTextView.setText(person.getName());
            mGradeTextView.setText(String.valueOf(person.getGrade()));
            mPhoneTextView.setText(person.getPhone());
            mNoteTextView.setText(person.getNote());
        }

        @Override
        public void onClick(View view) {
            mPresenter.onItemClicked(mItem);
        }

    }
}
