package com.kara4k.tutor18.presenter;


import android.util.Log;

import com.kara4k.tutor18.view.ListViewIF;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class ListPresenter<T, V extends ListViewIF<T>>
        implements Presenter, SingleObserver<List<T>> {

    @Inject
    protected V mListView;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public abstract void onItemClicked(T t);

    protected V getView() {
        return mListView;
    }

    protected void subscribe(Callable<List<T>> callable) {
        Single.fromCallable(callable)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        mCompositeDisposable.add(d);
    }

    @Override
    public void onSuccess(List<T> list) {
        mListView.setItems(list);
    }

    @Override
    public void onError(Throwable e) {
        mListView.showError(e.getMessage());
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
    }
}
