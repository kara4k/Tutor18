package com.kara4k.tutor18.view.activities;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.di.DaggerViewComponent;
import com.kara4k.tutor18.di.modules.ViewModule;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.other.FormatUtils;
import com.kara4k.tutor18.presenter.EventDetailsPresenter;
import com.kara4k.tutor18.view.EventDetailsIF;
import com.kara4k.tutor18.view.custom.EditTextDialog;
import com.kara4k.tutor18.view.custom.ItemView;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class EventDetails extends BaseActivity implements EventDetailsIF {

    public static final String EVENT_ID = "event_id";
    public static final String EMPTY = "";

    @BindView(R.id.time_item_view)
    ItemView mTimeItemView;
    @BindView(R.id.name_item_view)
    ItemView mNameItemView;
    @BindView(R.id.duration_item_view)
    ItemView mDurationItemView;
    @BindView(R.id.price_item_view)
    ItemView mPriceItemView;
    @BindView(R.id.subject_item_view)
    ItemView mSubjectItemView;
    @BindView(R.id.note_item_view)
    ItemView mNoteItemView;
    @BindView(R.id.payment_item_view)
    ItemView mPaymentItemView;
    @BindView(R.id.expected_item_view)
    ItemView mExpectedItemView;
    @BindView(R.id.state_item_view)
    ItemView mStateItemView;
    @BindView(R.id.rescheduled_to_layout)
    LinearLayout mReschToLayout;
    @BindView(R.id.rescheduled_to_day_item_view)
    ItemView mReschToDayItemView;
    @BindView(R.id.rescheduled_to_time_item_view)
    ItemView mReschToTimeItemView;
    @BindView(R.id.rescheduled_from_item_view)
    ItemView mReschFromItemView;
    @BindView(R.id.payment_layout)
    LinearLayout mPaymentLayout;

    @Inject
    EventDetailsPresenter mPresenter;
    Subject<Event> mEventObservable = PublishSubject.create();
    private Event mEvent;

    @Override
    protected int getContentView() {
        return R.layout.activity_event_details;
    }

    @Override
    protected void injectDaggerDependencies() {
        DaggerViewComponent.builder()
                .appComponent(getAppComponent())
                .viewModule(new ViewModule(this))
                .build().injectEventDetailsActivity(this);
    }

    @Override
    protected void onViewReady() {
        long eventId = getIntent().getLongExtra(EVENT_ID, -1);
        mPresenter.onStart(eventId);
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public void showDetails(Event event) {
        mEvent = event;
        mEventObservable.subscribe(this::updateUI, this::onError);
        mEventObservable.onNext(mEvent);
    }

    @Override
    public void closeView() {
        finish();
    }

    private void updateUI(Event event) {
        String time = FormatUtils.formatTime(event);

        String name = FormatUtils.formatName(event.getPerson());
        String duration = getString(R.string.event_duration, event.getLesson().getDuration());
        String price = FormatUtils.formatPrice(event.getLesson().getPrice());
        String lessonPrice = getString(R.string.event_price, price);

        mTimeItemView.setSummary(time);
        mNameItemView.setSummary(name);
        mDurationItemView.setSummary(duration);
        mPriceItemView.setSummary(lessonPrice);
        mSubjectItemView.setSummary(event.getSubjects());
        mNoteItemView.setSummary(event.getNote());
        setPayment(event);
        setRescheduledFrom(event);
        setState(event);

    }

    private void setPayment(Event event) {
        if (event.isPayment()) {
            String expected = FormatUtils.formatPrice(event.getExpectedPrice());
            String current = FormatUtils.formatPrice(event.getPrice());
            String expectedPrice = getString(R.string.event_price, expected);
            String currentPrice = getString(R.string.event_price, current);
            boolean isPaid = event.isPaid();

            mPaymentLayout.setVisibility(View.VISIBLE);
            mExpectedItemView.setSummary(expectedPrice);
            mPaymentItemView.setSummary(currentPrice);
            mPaymentItemView.setChecked(isPaid);
        } else {
            mPaymentLayout.setVisibility(View.GONE);
        }
    }

    private void setState(Event event) {
        switch (event.getState()) {
            case Event.UNDEFINED:
                String undefined = getString(R.string.array_event_undefined);

                mStateItemView.setSummary(undefined);
                mStateItemView.setIconImageResource(R.drawable.ic_help_outline_black_24dp);
                mReschToLayout.setVisibility(View.GONE);
                break;
            case Event.HELD:
                String wasHeld = getString(R.string.array_event_held);

                mStateItemView.setSummary(wasHeld);
                mStateItemView.setIconImageResource(R.drawable.ic_done_all_black_24dp);
                mReschToLayout.setVisibility(View.GONE);
                break;
            case Event.NOT_HELD:
                String wasNotHeld = getString(R.string.array_event_not_held);

                mStateItemView.setSummary(wasNotHeld);
                mStateItemView.setIconImageResource(R.drawable.ic_highlight_off_red_50_24dp);
                mReschToLayout.setVisibility(View.GONE);
                break;
            case Event.RESCHEDULED:
                String rescheduled = getString(R.string.array_event_rescheduled);

                mReschToLayout.setVisibility(View.VISIBLE);
                mStateItemView.setSummary(rescheduled);

                if (mEvent.getRescheduledToId() != null) {
                    Calendar calendar = Calendar.getInstance();

                    mStateItemView.setIconImageResource(R.drawable.ic_redo_black_24dp);
                    mReschFromItemView.setVisibility(View.GONE);

                    calendar.setTimeInMillis(mEvent.getRescheduledToId());
                    String day = FormatUtils.formatCalDay(calendar);
                    String time = FormatUtils.formatCalTime(calendar);

                    mReschToDayItemView.setSummary(day);
                    mReschToTimeItemView.setSummary(time);
                } else {
                    mReschToDayItemView.setSummary(EMPTY);
                    mReschToTimeItemView.setSummary(EMPTY);
                }
                break;
        }
    }

    private void setRescheduledFrom(Event event) {
        if (event.getRescheduledFromId() != null) {
            Calendar calendar = Calendar.getInstance();

            mReschFromItemView.setVisibility(View.VISIBLE);
            calendar.setTimeInMillis(event.getRescheduledFromId());

            String dateTime = FormatUtils.formatCalDateTime(calendar);
            mReschFromItemView.setSummary(dateTime);
        } else {
            mReschFromItemView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.subject_item_view)
    void onSubjectClicked() {
        String title = getString(R.string.dialog_subjects_title);
        String text = mEvent.getSubjects();
        EditTextDialog.OnOkListener listener = (s) -> {
            mEvent.setSubjects(s);
            mEventObservable.onNext(mEvent);
        };

        new EditTextDialog()
                .setTitle(title)
                .setText(text)
                .setOnOkListener(listener)
                .show(getSupportFragmentManager(), "subjects");
    }

    @OnClick(R.id.note_item_view)
    void onNoteClicked() {
        String title = getString(R.string.dialog_note_title);
        String text = mEvent.getNote();
        EditTextDialog.OnOkListener listener = (s) -> {
            mEvent.setNote(s);
            mEventObservable.onNext(mEvent);
        };

        new EditTextDialog()
                .setTitle(title)
                .setText(text)
                .setOnOkListener(listener)
                .show(getSupportFragmentManager(), "note");
    }

    @OnClick(R.id.payment_item_view)
    void onPaymentClick(ItemView itemView) {
        mEvent.setIsPaid(!itemView.isChecked());
        mEventObservable.onNext(mEvent);
    }

    @OnClick(R.id.state_item_view)
    void onHeldClicked() {
        String title = getString(R.string.dialog_state_title);
        String[] dialogItems = getResources().getStringArray(R.array.event_held);
        DialogInterface.OnClickListener listener = (dialogInterface, i) -> {
            mEvent.setState(i);
            if (i != Event.RESCHEDULED) mEvent.setRescheduledToId(null);
            mEventObservable.onNext(mEvent);
        };

        showItemsDialog(title, dialogItems, listener);
    }

    @OnClick(R.id.rescheduled_to_day_item_view)
    void onReschToDayClick() {
        Calendar calendar = Calendar.getInstance();

        if (mEvent.getRescheduledToId() != null) {
            calendar.setTimeInMillis(mEvent.getRescheduledToId());
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener listener = (datePicker, i, i1, i2) -> {
            calendar.set(i, i1, i2);
            mEvent.setRescheduledToId(calendar.getTimeInMillis());
            mEventObservable.onNext(mEvent);
        };

        new DatePickerDialog(this, R.style.PickerStyle, listener, year, month, day).show();
    }

    @OnClick(R.id.rescheduled_to_time_item_view)
    void onReschToTimeClick() {
        Calendar calendar = Calendar.getInstance();

        if (mEvent.getRescheduledToId() != null) {
            calendar.setTimeInMillis(mEvent.getRescheduledToId());
        }

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener listener = (timePicker, i, i1) -> {
            calendar.set(Calendar.HOUR_OF_DAY, i);
            calendar.set(Calendar.MINUTE, i1);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            mEvent.setRescheduledToId(calendar.getTimeInMillis());
            mEventObservable.onNext(mEvent);
        };

        new TimePickerDialog(this, R.style.PickerStyle, listener,
                hour, minutes, true).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_event_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_save_event:
                mPresenter.onSaveEvent(mEvent);
                return true;
            case R.id.menu_item_delete_event:
                String title = getString(R.string.dialog_delete_event_title);
                String message = getString(R.string.dialog_delete_event_message);
                DialogInterface.OnClickListener okListener = (dialogInterface, i)
                        -> mPresenter.onDeleteEvent(mEvent);

                showConfirmDialog(title, message, okListener);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onError(Throwable throwable) {
        showToast(throwable.getMessage());
    }

    public static Intent newIntent(Context context, long eventId) {
        Intent intent = new Intent(context, EventDetails.class);
        intent.putExtra(EVENT_ID, eventId);
        return intent;
    }
}
