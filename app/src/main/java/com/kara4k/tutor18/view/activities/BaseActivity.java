package com.kara4k.tutor18.view.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.di.AppComponent;
import com.kara4k.tutor18.other.App;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getContentView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        injectDaggerDependencies();
        onViewReady();
    }

    protected void setFragment(Fragment fragment) {
        FragmentManager sfm = getSupportFragmentManager();
        int container = R.id.fragment_container;

        Fragment currentFrag = sfm.findFragmentById(container);
        if (currentFrag == null) {
            sfm.beginTransaction().add(container, fragment).commit();
        } else {
            sfm.beginTransaction().replace(container, fragment).commit();
        }
    }

    protected AppComponent getAppComponent() {
        return ((App) getApplication()).getAppComponent();
    }

    protected void injectDaggerDependencies() {};

    protected void onViewReady(){};
}
