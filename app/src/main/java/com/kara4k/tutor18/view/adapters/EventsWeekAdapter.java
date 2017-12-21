package com.kara4k.tutor18.view.adapters;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.model.Event;
import com.kara4k.tutor18.model.WeekEvent;
import com.kara4k.tutor18.other.FormatUtils;
import com.kara4k.tutor18.presenter.EventsWeekPresenter;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.util.List;

import butterknife.BindView;

public class EventsWeekAdapter extends Adapter<WeekEvent, EventsWeekAdapter.DayHolder> {

    EventsWeekPresenter mPresenter;

    public EventsWeekAdapter(EventsWeekPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public DayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_week_events, parent, false);
        return new DayHolder(view);
    }

    class DayHolder extends Holder<WeekEvent> {

        @BindView(R.id.day_text_view)
        TextView mDayTextView;
        @BindView(R.id.date_text_view)
        TextView mDateTextView;
        @BindView(R.id.price_text_view)
        TextView mPriceTextView;
        @BindView(R.id.state_layout)
        LinearLayout mStateLayout;
        @BindView(R.id.payment_layout)
        LinearLayout mPaymentLayout;

        public DayHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(WeekEvent weekEvent) {
            super.onBind(weekEvent);
            List<Event> events = weekEvent.getEvents();

            setDay(weekEvent);
            setDate(weekEvent);
            mStateLayout.removeAllViews();
            mPaymentLayout.removeAllViews();

            if (events.isEmpty()) {
                mPriceTextView.setVisibility(View.GONE);
            } else {
                mPriceTextView.setVisibility(View.VISIBLE);
                BigDecimal total = BigDecimal.ZERO;
                for (int i = 0; i < events.size(); i++) {
                    Event event = events.get(i);
                    addEventState(event);
                    addEventPayment(event);
                    total = calculatePrice(total, event);
                }
                setPrice(total);
            }
        }

        private void addEventPayment(Event event) {
            if (event.isPayment()) {
                ImageView imageView = createPaymentImageView();
                if (!event.isPaid()) {
                    imageView.setColorFilter(Color.RED);
                }
                mPaymentLayout.addView(imageView);
            } else if (event.isPaid()) {
                ImageView imageView = createPaymentImageView();
                mPaymentLayout.addView(imageView);
            }
        }

        @NonNull
        private ImageView createPaymentImageView() {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(R.drawable.ic_attach_money_black_18dp);
            return imageView;
        }

        protected void setPrice(BigDecimal total) {
            String totalPrice = FormatUtils.formatPrice(total.doubleValue());
            String dayPrice = mContext.getString(R.string.price, totalPrice);
            mPriceTextView.setText(dayPrice);
        }

        protected BigDecimal calculatePrice(BigDecimal total, Event event) {
            if (event.getState() == Event.HELD) {
                BigDecimal price = new BigDecimal(event.getLesson().getPrice());
                total = total.add(price);
            }
            return total;
        }

        protected void addEventState(Event event) {
            int stateIconRes = FormatUtils.getStateIconRes(event);
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(stateIconRes);
            mStateLayout.addView(imageView);
        }

        protected void setDate(WeekEvent weekEvent) {
            String[] months = DateFormatSymbols.getInstance().getMonths();
            String monthName = months[weekEvent.getMonth()];
            String date = weekEvent.getDayOfMonth() + " " + monthName;
            mDateTextView.setText(date);
        }

        protected void setDay(WeekEvent weekEvent) {
            String[] weekdays = FormatUtils.getSortedDays();
            int index = FormatUtils.getDayDbValue(weekEvent.getDayOfWeek());
            mDayTextView.setText(weekdays[index]);
        }

        @Override
        public void onClick(View view) {
            mPresenter.onItemClicked(mItem);
        }
    }
}
