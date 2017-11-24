package com.kara4k.tutor18.view.fragments;


import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.di.AppComponent;
import com.kara4k.tutor18.other.App;
import com.kara4k.tutor18.view.adapters.Adapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseListFragment extends Fragment {

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    protected abstract Adapter getAdapter();

    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        injectDaggerDependencies();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(getAdapter());

        ButterKnife.bind(this, view);
        return view;
    }

    protected void injectDaggerDependencies() {}

    protected void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected AppComponent getAppComponent(){
        return ((App) getActivity().getApplication()).getAppComponent();
    }
}
