package com.kara4k.tutor18.view.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.di.DaggerViewComponent;
import com.kara4k.tutor18.di.modules.ViewModule;
import com.kara4k.tutor18.presenter.StatsPresenter;
import com.kara4k.tutor18.presenter.vo.Stat;
import com.kara4k.tutor18.view.StatsIF;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;

public class StatsFragment extends BaseFragment implements StatsIF {

    @BindView(R.id.day_completed)
    TextView mDayComplete;
    @BindView(R.id.day_canceled)
    TextView mDayCancel;
    @BindView(R.id.day_owned)
    TextView mDayOwned;
    @BindView(R.id.day_paid)
    TextView mDayPaid;
    @BindView(R.id.week_completed)
    TextView mWeekComplete;
    @BindView(R.id.week_canceled)
    TextView mWeekCancel;
    @BindView(R.id.week_owned)
    TextView mWeekOwned;
    @BindView(R.id.week_paid)
    TextView mWeekPaid;
    @BindView(R.id.month_completed)
    TextView mMonthComplete;
    @BindView(R.id.month_canceled)
    TextView mMonthCancel;
    @BindView(R.id.month_owned)
    TextView mMonthOwned;
    @BindView(R.id.month_paid)
    TextView mMonthPaid;

    @Inject
    StatsPresenter mPresenter;
    private Calendar mCalendar = Calendar.getInstance();

    @Override
    protected int getLayout() {
        return R.layout.fragment_stats;
    }

    @Override
    protected int getMenuRes() {
        return R.menu.fragment_stats;
    }

    @Override
    public Calendar getCalendar() {
        return mCalendar;
    }

    @Override
    protected void injectDaggerDependencies() {
        DaggerViewComponent.builder()
                .appComponent(getAppComponent())
                .viewModule(new ViewModule(this))
                .build().injectStatsFrag(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart(mCalendar.getTimeInMillis());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_select_date:
                DatePickerDialog.OnDateSetListener listener = (datePicker, i, i1, i2) -> {
                    mCalendar.set(i, i1, i2);
                    mPresenter.onStart(mCalendar.getTimeInMillis());
                };

                showDatePicker(listener);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    public static StatsFragment newInstance() {
        Bundle args = new Bundle();
        StatsFragment fragment = new StatsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setStats(Stat stat) {
      switch (stat.getType()) {
          case Stat.TYPE_DAY:
              mDayComplete.setText(stat.getCompleted());
              mDayCancel.setText(stat.getCanceled());
              mDayOwned.setText(stat.getOwned());
              mDayPaid.setText(stat.getPaid());
              break;
          case Stat.TYPE_WEEK:
              mWeekComplete.setText(stat.getCompleted());
              mWeekCancel.setText(stat.getCanceled());
              mWeekOwned.setText(stat.getOwned());
              mWeekPaid.setText(stat.getPaid());
              break;
          case Stat.TYPE_MONTH:
              mMonthComplete.setText(stat.getCompleted());
              mMonthCancel.setText(stat.getCanceled());
              mMonthOwned.setText(stat.getOwned());
              mMonthPaid.setText(stat.getPaid());
              break;
      }
    }
}
