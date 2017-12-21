package com.kara4k.tutor18.view.adapters;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.model.Lesson;
import com.kara4k.tutor18.model.Person;
import com.kara4k.tutor18.other.FormatUtils;
import com.kara4k.tutor18.presenter.EventsDayPresenter;

import butterknife.BindView;

public class EventsDayAdapter extends Adapter<Event, EventsDayAdapter.EventHolder> {

    private EventsDayPresenter mPresenter;

    public EventsDayAdapter(EventsDayPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_day_event, parent, false);
        return new EventHolder(view);
    }

    class EventHolder extends Holder<Event> {

        @BindView(R.id.time_text_view)
        TextView mTimeTextView;
        @BindView(R.id.name_text_view)
        TextView mNameTextView;
        @BindView(R.id.duration_text_view)
        TextView mDurationTextView;
        @BindView(R.id.price_text_view)
        TextView mPriceTextView;
        @BindView(R.id.payment_image_view)
        ImageView mPaymentImageView;
        @BindView(R.id.month_price_text_view)
        TextView mMonthPriceTextView;
        @BindView(R.id.state_image_view)
        ImageView mStateImageView;
        @BindView(R.id.payment_layout)
        LinearLayout mPaymentLayout;
        @BindView(R.id.rescheduled_from_image_view)
        ImageView mReschFromImageView;
        @BindView(R.id.subjects_text_view)
        TextView mSubjectsTextView;

        public EventHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(Event event) {
            super.onBind(event);
            Lesson lesson = event.getLesson();
            Person person = event.getPerson();

            mTimeTextView.setText(FormatUtils.formatTime(event));
            mNameTextView.setText(String.format("%s %s", person.getFirstName(), person.getName()));
            mSubjectsTextView.setText(event.getSubjects());

            String lessonDuration = mContext.getString(
                    R.string.event_duration, lesson.getDuration());
            mDurationTextView.setText(lessonDuration);

            String price = FormatUtils.formatPrice(lesson.getPrice());
            String lessonPrice = mContext.getString(R.string.price, price);
            mPriceTextView.setText(lessonPrice);

            int visibility = event.getRescheduledFromId() == null ? View.GONE : View.VISIBLE;
            mReschFromImageView.setVisibility(visibility);

            setPayment(event);

            int stateIconRes = FormatUtils.getStateIconRes(event);
            mStateImageView.setImageResource(stateIconRes);
        }

        private void setPayment(Event event) {
            String currentPrice = FormatUtils.formatPrice(event.getPrice());

            if (event.isPayment()) {
                setIsPaid(currentPrice);
                setPaymentColors(event);
            } else if (event.isPaid()) {
                setIsPaid(currentPrice);
            } else {
                mPaymentLayout.setVisibility(View.GONE);
            }
        }

        private void setIsPaid(String currentPrice) {
            mPaymentLayout.setVisibility(View.VISIBLE);
            mMonthPriceTextView.setText(currentPrice);
        }

        private void setPaymentColors(Event event) {
            int color = event.isPaid() ? Color.BLACK : Color.RED;
            mMonthPriceTextView.setTextColor(color);
            mPaymentImageView.setColorFilter(color);

        }

        @Override
        public void onClick(View view) {
            mPresenter.onItemClicked(mItem);
        }
    }
}
