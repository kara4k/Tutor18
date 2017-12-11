package com.kara4k.tutor18.view.activities;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.di.DaggerViewComponent;
import com.kara4k.tutor18.di.modules.ViewModule;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.other.FormatUtils;
import com.kara4k.tutor18.presenter.EventDetailsPresenter;
import com.kara4k.tutor18.view.EventDetailsIF;
import com.kara4k.tutor18.view.custom.ItemView;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class EventDetails extends BaseActivity implements EventDetailsIF {

    public static final String EVENT_ID = "event_id";

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
    @BindView(R.id.is_held_item_view)
    ItemView mIsHeldItemView;
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
    public void showDetails(Event event) {
        mEvent = event;
        mEventObservable.subscribe(this::updateUI, this::onError);
        mEventObservable.onNext(mEvent);
    }

    private void updateUI(Event event) {
        String time = FormatUtils.formatTime(event.getLesson());
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
        setHeld(event);

    }

    private void setPayment(Event event) {
        if (event.isPayment()) {
            String expected = FormatUtils.formatPrice(event.getExpectedPrice());
            String current = FormatUtils.formatPrice(event.getMonthPrice());
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

    private void setHeld(Event event) {
        switch (event.getIsHeld()) {
            case Event.UNDEFINED:
                mIsHeldItemView.setSummary("");
                mIsHeldItemView.setIconImageResource(R.drawable.ic_help_outline_black_24dp);
                mReschToLayout.setVisibility(View.GONE);
                mReschFromItemView.setVisibility(View.GONE);
                break;
            case Event.HELD:
                mIsHeldItemView.setSummary("Проведено");
                mIsHeldItemView.setIconImageResource(R.drawable.ic_done_all_black_24dp);
                mReschFromItemView.setVisibility(View.GONE);
                mReschToLayout.setVisibility(View.GONE);
                break;
            case Event.NOT_HELD:
                mIsHeldItemView.setSummary("Отменено");
                mIsHeldItemView.setIconImageResource(R.drawable.ic_highlight_off_red_50_24dp);
                mReschFromItemView.setVisibility(View.GONE);
                mReschToLayout.setVisibility(View.GONE);
                break;
            case Event.RESCHEDULED:
                Calendar calendar = Calendar.getInstance();
                mReschToLayout.setVisibility(View.VISIBLE);

                mIsHeldItemView.setSummary("Перенесено на:");
                if (mEvent.getRescheduledToId() != 0) {
                    mIsHeldItemView.setIconImageResource(R.drawable.ic_redo_black_24dp);
                    mReschFromItemView.setVisibility(View.GONE);

                    calendar.setTimeInMillis(mEvent.getRescheduledToId());
                    String day = FormatUtils.formatCalDay(calendar);
                    String time = FormatUtils.formatCalTime(calendar);
                    mReschToDayItemView.setSummary(day);
                    mReschToTimeItemView.setSummary(time);
                }
                if (mEvent.getRescheduledFromId() != 0) {
                    mReschFromItemView.setVisibility(View.VISIBLE);
                    calendar.setTimeInMillis(mEvent.getRescheduledFromId());
                    String dateTime = FormatUtils.formatCalDateTime(calendar);
                    mReschFromItemView.setSummary(dateTime);
                }
                break;
        }
    }

    @OnClick(R.id.subject_item_view)
    void onTimeClicked() {
        mEvent.setSubjects("opop");
        mEventObservable.onNext(mEvent);
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
