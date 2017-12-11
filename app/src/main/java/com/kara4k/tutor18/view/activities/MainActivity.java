package com.kara4k.tutor18.view.activities;

import android.view.MenuItem;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.view.fragments.EventsDayFragment;
import com.kara4k.tutor18.view.fragments.PersonsListFragment;

public class MainActivity extends DrawerActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        setFragment(new PersonsListFragment());
    }

    @Override
    protected void onNavItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_events:
                setFragment(EventsDayFragment.newInstance());
                break;
            case R.id.nav_persons:
                setFragment(PersonsListFragment.newInstance());
                break;
            case R.id.nav_send:  // TODO: 08.12.2017
                startActivity(EventsActivity.newIntent(this));
                break;
        }
    }
}
