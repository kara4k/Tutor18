package com.kara4k.tutor18.view.adapters;


import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public abstract class Holder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected Context mContext;
    protected T mItem;

    public Holder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
        ButterKnife.bind(this,itemView);
    }

    @CallSuper
    public void onBind(T t){
        mItem = t;
    }
}
