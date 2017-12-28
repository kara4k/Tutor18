package com.kara4k.tutor18.view.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.kara4k.tutor18.R;

import java.util.Calendar;

import butterknife.BindView;

public class ViewPagerFragment extends BaseFragment {

    public static final String TIMESTAMP = "timestamp";

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private Calendar mCalendar = Calendar.getInstance();

    @Override
    protected int getLayout() {
        return R.layout.fragment_view_pager;
    }

    @Override
    protected int getMenuRes() {
        return R.menu.fragment_view_pager;
    }

    @Override
    public Calendar getCalendar() {
        return mCalendar;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            long timestamp = getArguments().getLong(TIMESTAMP);
            mCalendar.setTimeInMillis(timestamp);
        }
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new Adapter(getChildFragmentManager()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_select_date:
                DatePickerDialog.OnDateSetListener listener = (datePicker, i, i1, i2) -> {
                    int currentItem = mViewPager.getCurrentItem();
                    mCalendar.set(i, i1, i2);
                    mViewPager.setAdapter(new Adapter(getChildFragmentManager()));
                    mViewPager.setCurrentItem(currentItem);
                };

                showDatePicker(listener);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static ViewPagerFragment newInstance() {
        return new ViewPagerFragment();
    }

    public static ViewPagerFragment newInstance(long timeInMillis) {
        Bundle args = new Bundle();
        args.putLong(TIMESTAMP, timeInMillis);
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    class Adapter extends FragmentStatePagerAdapter {

        CharSequence[] mTitles;

        public Adapter(FragmentManager fm) {
            super(fm);
            setupTitles();
        }

        private void setupTitles() {
            mTitles = new CharSequence[2];
            mTitles[0] = getString(R.string.tab_layout_day);
            mTitles[1] = getString(R.string.tab_layout_week);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return EventsDayFragment.newInstance(mCalendar.getTimeInMillis());
                case 1:
                    return EventsWeekFragment.newInstance(mCalendar.getTimeInMillis());
                default:
                    return new Fragment();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
