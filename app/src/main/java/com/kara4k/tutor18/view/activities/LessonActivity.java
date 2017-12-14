package com.kara4k.tutor18.view.activities;


import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.view.View;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.model.Lesson;
import com.kara4k.tutor18.other.FormatUtils;
import com.kara4k.tutor18.view.custom.EditTextDialog;
import com.kara4k.tutor18.view.custom.ItemView;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

public class LessonActivity extends BaseActivity {

    public static final String LESSON = "lesson";

    @BindView(R.id.day_item_view)
    ItemView mDayItemView;
    @BindView(R.id.start_item_view)
    ItemView mStartItemView;
    @BindView(R.id.duration_item_view)
    ItemView mDurationItemView;
    @BindView(R.id.price_item_view)
    ItemView mPriceItemView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private Lesson mLesson;

    @Override
    protected int getContentView() {
        return R.layout.activity_lesson;
    }

    @Override
    protected void onViewReady() {
        mLesson = (Lesson) getIntent().getSerializableExtra(LESSON);

        if (mLesson == null) mLesson = new Lesson();

        String day = FormatUtils.getSortedDays()[mLesson.getDayOfWeek()];
        String time = FormatUtils.formatTime(mLesson.getStartHour(), mLesson.getStartMin());
        String duration = String.valueOf(mLesson.getDuration());
        String price = FormatUtils.formatPrice(mLesson.getPrice());

        mDayItemView.setSummary(day);
        mStartItemView.setSummary(time);
        mDurationItemView.setSummary(duration);
        mPriceItemView.setSummary(price);
    }

    @OnClick(R.id.day_item_view)
    void onDayClick(View view) {
        String title = getString(R.string.lesson_dialog_day_title);
        String[] weekdays = FormatUtils.getSortedDays();
        DialogInterface.OnClickListener listener = (di, i) -> {
            mDayItemView.setSummary(weekdays[i]);
            mLesson.setDayOfWeek(i);
        };

        showItemsDialog(title, weekdays, listener);
    }

    @OnClick(R.id.start_item_view)
    void onStartClick(View view) {
        int startHour = mLesson.getStartHour();
        int startMin = mLesson.getStartMin();
        TimePickerDialog.OnTimeSetListener listener = (tp, h, m) -> {
            mLesson.setStartHour(h);
            mLesson.setStartMin(m);
            mStartItemView.setSummary(FormatUtils.formatTime(h, m));
        };

        new TimePickerDialog(this, R.style.PickerStyle
                , listener, startHour, startMin, true)
                .show();
    }

    @OnClick(R.id.duration_item_view)
    void onDurationClick(View view) {
        String title = getString(R.string.dialog_lesson_duration_title);
        String[] durationItems = getResources().getStringArray(R.array.lesson_duration_array);
        DialogInterface.OnClickListener listener = (di, i) -> {
            mDurationItemView.setSummary(durationItems[i]);
            mLesson.setDuration(Integer.parseInt(durationItems[i]));
        };

        showItemsDialog(title, durationItems, listener);
    }

    @OnClick(R.id.price_item_view)
    void onPriceClick(View view) {
        String title = getString(R.string.dialog_lesson_price_title);
        String text = FormatUtils.formatPrice(mLesson.getPrice());
        int inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL;
        EditTextDialog.OnOkListener okListener = (s) -> {
            BigDecimal price = FormatUtils.formatPrice(s);
            mPriceItemView.setSummary(price.toPlainString());
            mLesson.setPrice(price.doubleValue());
        };

        new EditTextDialog()
                .setTitle(title)
                .setText(text)
                .setInputType(inputType)
                .setOnOkListener(okListener)
                .show(getSupportFragmentManager(), "dialog");
    }


    @OnClick(R.id.fab)
    public void onFabClick() {
        Intent intent = new Intent();
        intent.putExtra(LESSON, mLesson);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }



    public static Intent newIntent(Context context) {
        return new Intent(context, LessonActivity.class);
    }

    public static Intent newIntent(Context context, Lesson lesson) {
        Intent intent = new Intent(context, LessonActivity.class);
        intent.putExtra(LESSON, lesson);
        return intent;
    }
}
