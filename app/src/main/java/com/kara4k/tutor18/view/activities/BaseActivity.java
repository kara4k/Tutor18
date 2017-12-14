package com.kara4k.tutor18.view.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

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

    protected void hideSoftKeyboard() {
        try {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext()
                        .getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showConfirmDialog(String title, String text, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(text)
                .setPositiveButton(android.R.string.ok, okListener)
                .setNegativeButton(android.R.string.cancel, null)
                .create().show();
    }

    protected void showItemsDialog(String title, CharSequence[] items,
                                   DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setItems(items, listener)
                .create()
                .show();
    }

    protected void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected AppComponent getAppComponent() {
        return ((App) getApplication()).getAppComponent();
    }

    protected void injectDaggerDependencies() {};

    protected void onViewReady(){};
}
