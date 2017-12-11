package com.kara4k.tutor18.view.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        @BindView(R.id.held_image_view)
        ImageView mHeldImageView;

        public EventHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(Event event) {
            super.onBind(event);
            Lesson lesson = mItem.getLesson();
            Person person = mItem.getPerson();

            mTimeTextView.setText(FormatUtils.formatTime(lesson));
            mNameTextView.setText(String.format("%s %s", person.getFirstName(), person.getName()));

            String lessonDuration = mContext.getString(
                    R.string.event_duration, lesson.getDuration());
            mDurationTextView.setText(lessonDuration);

            String price = FormatUtils.formatPrice(lesson.getPrice());
            String lessonPrice = mContext.getString(R.string.event_price, price);
            mPriceTextView.setText(lessonPrice);

            if (event.isPayment()) {
                String expectedPrice = FormatUtils.formatPrice(event.getExpectedPrice());

                mPaymentImageView.setVisibility(View.VISIBLE);
                mMonthPriceTextView.setVisibility(View.VISIBLE);
                mMonthPriceTextView.setText(expectedPrice);
            } else {
                mPaymentImageView.setVisibility(View.GONE);
                mMonthPriceTextView.setVisibility(View.GONE);
            }


        }

        private int getHeldImageRes(Event event) { // TODO: 09.12.2017
            switch (event.getHeld()) {
                case Event.UNDEFINED:

            }
            return 0;
        }

        @Override
        public void onClick(View view) {
            mPresenter.onItemClicked(mItem);
        }
    }
}
