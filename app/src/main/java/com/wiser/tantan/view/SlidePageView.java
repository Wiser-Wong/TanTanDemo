package com.wiser.tantan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Vector;

/**
 * @author Wiser
 */
public class SlidePageView extends FrameLayout {

    private LayoutInflater mInflater;

    private Vector<View> views = new Vector<>();

    private int shrinkDistance = 30;

    private SlidePageAdapter adapter;

    private boolean isInit = true;

    public SlidePageView(Context context) {
        super(context);
        init();
    }

    public SlidePageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mInflater = LayoutInflater.from(getContext());
    }

    public int getShrinkDistance() {
        return shrinkDistance;
    }

    public <T> void setAdapter(SlidePageAdapter<T> adapter) {
        if (adapter == null) return;

        this.adapter = adapter;

        if (isInit) {
            adapter.initAdapter(this, getContext(), mInflater);
            isInit = false;
        }

        for (int i = 0; i < adapter.getCounts(); i++) {
            View view = adapter.getItemView(this, i);
            views.add(0, view);
        }

        cardView(true, 0);

    }

    public void cardView(boolean isInit, int move) {
        if (isInit) {
            removeAllViews();
        }
        for (int i = 0; i < views.size(); i++) {
            if (isInit) {
                addView(views.get(i));
            }
            View view = views.get(i);
            MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
            if (i == views.size() - 1) {
                params.bottomMargin = 2 * shrinkDistance;
                view.setLayoutParams(params);
                break;
            }
            params.leftMargin = shrinkDistance - i * (shrinkDistance / (views.size() - 1)) - move;
            params.rightMargin = shrinkDistance - i * (shrinkDistance / (views.size() - 1)) - move;
            params.topMargin = 2 * shrinkDistance - i * ((2 * shrinkDistance) / (views.size() - 1)) - 2 * move;
            params.bottomMargin = 2 * shrinkDistance - (views.size() - i - 1) * ((2 * shrinkDistance) / (views.size() - 1)) + 2 * move;
            view.setLayoutParams(params);
        }
    }

    public void removeV(View view) {
        if (views != null) views.remove(view);
        removeView(view);
        if (adapter != null) adapter.removeTopView();
    }

    public void notifyDataAdapter() {
        views.clear();
        removeAllViews();
        setAdapter(adapter);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mInflater = null;
        if (views != null) {
            views.clear();
            views = null;
        }
        if (adapter != null) {
            adapter.detach();
            adapter = null;
        }
    }
}
