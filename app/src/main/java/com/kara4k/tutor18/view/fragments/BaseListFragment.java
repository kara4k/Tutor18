package com.kara4k.tutor18.view.fragments;


import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kara4k.tutor18.R;
import com.kara4k.tutor18.view.ListViewIF;
import com.kara4k.tutor18.view.adapters.Adapter;
import com.kara4k.tutor18.view.adapters.Holder;

import java.util.List;

import butterknife.BindView;

public abstract class BaseListFragment<T> extends BaseFragment implements ListViewIF<T> {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private Adapter<T, Holder<T>> mAdapter;

    protected abstract Adapter<T, Holder<T>> getAdapter();

    @Override
    protected int getLayout() {
        return R.layout.recycler_view;
    }

    @Override
    protected int getMenuRes() {
        return R.menu.fragment_base_list;
    }

    @CallSuper
    @Override
    protected void onViewReady() {
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(mAdapter = getAdapter());
    }

    @NonNull
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void setItems(List<T> list) {
        mAdapter.setList(list);
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }


}
