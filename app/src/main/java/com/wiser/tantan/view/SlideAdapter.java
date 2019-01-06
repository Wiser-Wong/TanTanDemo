package com.wiser.tantan.view;

import android.view.View;
import android.view.ViewGroup;

interface SlideAdapter<T> {

    int getCounts();

    T getItem(int position);

    View getItemView(ViewGroup viewGroup, int position);

}
