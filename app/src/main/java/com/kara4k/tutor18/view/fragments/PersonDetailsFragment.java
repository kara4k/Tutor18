package com.kara4k.tutor18.view.fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.model.Lesson;
import com.kara4k.tutor18.model.Person;
import com.kara4k.tutor18.other.FormatUtils;
import com.kara4k.tutor18.view.activities.LessonActivity;
import com.kara4k.tutor18.view.activities.PersonActivity;
import com.kara4k.tutor18.view.custom.ItemView;

import java.util.List;

import butterknife.BindView;

public class PersonDetailsFragment extends BaseFragment {

    public static final String PERSON = "person";
    private static final int NEW_LESSON = 1;

    @BindView(R.id.name_item_view)
    ItemView mNameItemView;
    @BindView(R.id.grade_item_view)
    ItemView mGradeItemView;
    @BindView(R.id.phone_item_view)
    ItemView mPhoneItemView;
    @BindView(R.id.note_item_view)
    ItemView mNoteItemView;
    @BindView(R.id.lessons_layout)
    LinearLayout mLessonsLayout;

    private Person mPerson;
    private List<Lesson> mLessons;

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

        mLessons = mPerson.getLessons();
        for (int i = 0; i < mLessons.size(); i++) {
            addLessonView(mLessons.get(i));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add_lesson:
                Intent intent = LessonActivity.newIntent(getContext());
                startActivityForResult(intent, NEW_LESSON);
                return true;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case NEW_LESSON:
                    Lesson lesson = (Lesson) data.getSerializableExtra(LessonActivity.LESSON);
                    if (lesson == null) return;
                    mPerson.addLesson(lesson);
                    ((PersonActivity) getActivity()).onSavePerson(mPerson);
                    break;
            }
        }
    }

    private void addLessonView(Lesson lesson) {

        ItemView itemView = new ItemView(getContext());
        itemView.setTitle(FormatUtils.getSortedDays()[lesson.getDayOfWeek()]);
        itemView.setSummary(String.valueOf(lesson.getDuration()));
        itemView.setIconVisibility(View.VISIBLE);
        itemView.setIconImageResource(R.drawable.ic_remove_black_24dp);

        mLessonsLayout.addView(itemView);
    }

    public static PersonDetailsFragment newInstance(Person person) {
        Bundle args = new Bundle();
        args.putSerializable(PERSON, person);
        PersonDetailsFragment fragment = new PersonDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
