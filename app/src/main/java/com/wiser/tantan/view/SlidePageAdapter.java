package com.wiser.tantan.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class SlidePageAdapter<T> implements SlideAdapter<T> {

    private LayoutInflater mInflater;

    private Context context;

    protected void initAdapter(Context context, LayoutInflater inflater) {
        this.context = context;
        this.mInflater = inflater;
    }

    protected Context context() {
        return context;
    }

    protected View inflater(ViewGroup viewGroup, int id) {
        if (mInflater == null) return null;
        return mInflater.inflate(id, viewGroup, false);
    }

}
