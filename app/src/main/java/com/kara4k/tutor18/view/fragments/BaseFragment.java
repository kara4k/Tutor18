package com.kara4k.tutor18.view.fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.di.AppComponent;
import com.kara4k.tutor18.other.App;

import java.util.Calendar;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    protected abstract int getLayout();

    protected abstract int getMenuRes();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        injectDaggerDependencies();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        onViewReady();
        return view;
    }

    protected void injectDaggerDependencies() {
    }

    protected void onViewReady() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(getMenuRes(), menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    protected void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showConfirmDialog(String title, String text, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(text)
                .setPositiveButton(android.R.string.ok, okListener)
                .setNegativeButton(android.R.string.cancel, null)
                .create().show();
    }

    protected void showDatePicker(DatePickerDialog.OnDateSetListener listener) {
        new DatePickerDialog(getContext(), R.style.PickerStyle, listener,
                getYear(), getMonth(), getDay())
                .show();
    }

    protected Calendar getCalendar() {
        return Calendar.getInstance();
    }

    private int getYear() {
        return getCalendar().get(Calendar.YEAR);
    }

    private int getMonth() {
        return getCalendar().get(Calendar.MONTH);
    }

    private int getDay() {
        return getCalendar().get(Calendar.DAY_OF_MONTH);
    }

    protected AppComponent getAppComponent() {
        return ((App) getActivity().getApplication()).getAppComponent();
    }
}
