package com.kara4k.tutor18.view.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.model.Person;
import com.kara4k.tutor18.view.activities.PersonActivity;
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

    private Person mPerson;

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
        mPerson = (Person) getArguments().getSerializable(PERSON);

        if (mPerson == null) return;

        mNameItemView.setTitle(mPerson.getFirstName());
        mNameItemView.setSummary(mPerson.getName());
        mGradeItemView.setSummary(mPerson.getGrade());
        mPhoneItemView.setSummary(mPerson.getPhone());
        mNoteItemView.setSummary(mPerson.getNote());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_edit:
                ((PersonActivity) getActivity()).onEditPerson(mPerson);
                return true;
            case R.id.menu_item_delete:
                String title = getString(R.string.delete_dialog_person_title);
                String text = getString(R.string.dialog_delete_person_text);
                DialogInterface.OnClickListener listener = (d, i) ->
                        ((PersonActivity) getActivity()).onDeletePerson(mPerson);

                showDialog(title, text, listener);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static PersonDetailsFragment newInstance(Person person) {
        Bundle args = new Bundle();
        args.putSerializable(PERSON, person);
        PersonDetailsFragment fragment = new PersonDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
