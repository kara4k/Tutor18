package com.kara4k.tutor18.view.activities;

import android.view.Menu;
import android.view.MenuItem;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.view.fragments.PersonsListFragment;

public class MainActivity extends DrawerActivity{


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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNavItemSelected(MenuItem item) {

    }
}
