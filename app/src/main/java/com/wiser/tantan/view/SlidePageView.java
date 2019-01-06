package com.wiser.tantan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Vector;

public class SlidePageView extends FrameLayout {

    private LayoutInflater mInflater;

    private Vector<View> views = new Vector<>();

    private int shrinkDistance = 20;

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

        adapter.initAdapter(getContext(), mInflater);

        for (int i = 0; i < adapter.getCounts(); i++) {
            View view = adapter.getItemView(this, i);
            views.add(0, view);
        }

        for (int i = 0; i < views.size(); i++) {
            addView(views.get(i));
            View view = views.get(i);
            MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
            if (i == views.size() - 1) {
                params.bottomMargin = 2 * shrinkDistance;
                continue;
            }
            if (i == 0) {
                params.leftMargin = shrinkDistance;
                params.rightMargin = shrinkDistance;
                params.topMargin = 2 * shrinkDistance;
                params.bottomMargin = 0;
                continue;
            }
            params.leftMargin = shrinkDistance - i * (shrinkDistance / (views.size() - 1));
            params.rightMargin = shrinkDistance - i * (shrinkDistance / (views.size() - 1));
            params.topMargin = 2 * shrinkDistance - i * ((2 * shrinkDistance) / (views.size() - 1));
            params.bottomMargin = 2 * shrinkDistance - (views.size() - i - 1) * ((2 * shrinkDistance) / (views.size() - 1));
        }
    }

}
