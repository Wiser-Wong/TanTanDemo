package com.wiser.tantan.view;

import android.animation.TypeEvaluator;
import android.graphics.Point;

public class BesselTypeEvaluator implements TypeEvaluator<Point> {
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        int x = (int) (startValue.x + (endValue.x - startValue.x) * fraction);
        int y = (int) (startValue.y + (endValue.y - startValue.y) * fraction);
        return new Point(x, y);
    }
}
