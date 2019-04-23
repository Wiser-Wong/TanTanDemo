package com.wiser.tantan.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @param <T>
 * @author Wiser
 */
public abstract class SlidePageAdapter<T> implements SlideAdapter<T> {

    private LayoutInflater mInflater;

    private Context context;

    private SlidePageView pageView;

    protected void initAdapter(SlidePageView pageView, Context context, LayoutInflater inflater) {
        this.context = context;
        this.mInflater = inflater;
        this.pageView = pageView;
    }

    protected Context context() {
        return context;
    }

    protected View inflater(ViewGroup viewGroup, int id) {
        if (mInflater == null) return null;
        return mInflater.inflate(id, viewGroup, false);
    }

    protected void notifyDataAdapter() {
        if (pageView != null) pageView.notifyDataAdapter();
    }

    protected void detach() {
        mInflater = null;
        context = null;
        pageView = null;
    }

}
