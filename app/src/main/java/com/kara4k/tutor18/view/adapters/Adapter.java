package com.kara4k.tutor18.view.adapters;


import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class Adapter<T, H extends Holder<T>> extends RecyclerView.Adapter<H> {

    private List<T> mList;

       @Override
    public void onBindViewHolder(H holder, int position) {
        holder.onBind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setList(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
