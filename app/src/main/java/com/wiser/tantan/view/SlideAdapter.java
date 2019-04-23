package com.wiser.tantan.view;

import android.view.View;
import android.view.ViewGroup;

/**
 * @param <T>
 * @author Wiser
 */
interface SlideAdapter<T> {

    int getCounts();

    T getItem(int position);

    View getItemView(ViewGroup viewGroup, int position);

    void removeTopView();
}
