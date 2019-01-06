package com.wiser.tantan.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wiser.tantan.NearbyPeopleModel;
import com.wiser.tantan.R;

public class SlideLayout extends FrameLayout implements View.OnTouchListener {

    private RoundedImageView ivNearbyPeople;

    private float downX, downY, animStartX, animStartY;

    private float x, y;

    private boolean isRunningAnim = false;

    private static final int TOP_SLIDE = 0X10002;
    private static final int BOTTOM_SLIDE = 0X10003;
    private static final int LEFT_SLIDE = 0X10004;
    private static final int RIGHT_SLIDE = 0X10005;
    private static final int NO_SLIDE = 0X10006;

    private int direction = NO_SLIDE;

    private ViewGroup parent;

    private SlidePageView pageView;

    private View parentChildView;

    public SlideLayout(Context context) {
        super(context);
        init();
    }

    public SlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_slide, null);
        ivNearbyPeople = view.findViewById(R.id.iv_nearby_people);
        addView(view);
        setOnTouchListener(this);
    }

    public void slideData(NearbyPeopleModel model) {
        if (model == null) return;
        Glide.with(getContext()).load(model.url).into(ivNearbyPeople);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getRawX();
                float moveY = event.getRawY();
                setDirection(moveX, moveY);
                setX(moveX - downX + x);
                setY(moveY - downY + y);
                setRotation(-(moveX - downX - x) / (getMeasuredWidth() / 5));
                break;
            case MotionEvent.ACTION_UP:
                animStartX = event.getRawX() - downX + x;
                animStartY = event.getRawY() - downY + y;
                if (isSlideHalf()) {
                    removeAnim();
                } else {
                    resetAnim();
                }
                break;
        }
        return true;
    }

    private boolean isSlideHalf() {
        return Math.abs(animStartX) > getWidth() / 2;
    }

    private void setDirection(float moveX, float moveY) {
        float x = moveX - downX;
        float y = moveY - downY;

        if (x < -150 && Math.abs(x) > Math.abs(y) + 150) {
            direction = LEFT_SLIDE;
        } else if (y < -150 && Math.abs(y) > Math.abs(x) + 150) {
            direction = TOP_SLIDE;
        } else if (x > 150 && x > Math.abs(y) + 150) {
            direction = RIGHT_SLIDE;
        } else if (y > 150 && y > Math.abs(x) + 150) {
            direction = BOTTOM_SLIDE;
        } else {
            direction = NO_SLIDE;
        }
    }

    public int getDirection() {
        return direction;
    }

    private void resetAnim() {
        if (isRunningAnim) return;
        isRunningAnim = true;
        ValueAnimator animator = ValueAnimator.ofObject(new BesselTypeEvaluator(), new Point((int) animStartX, (int) animStartY), new Point((int) x, (int) y));
        animator.setDuration(200);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isRunningAnim = false;
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                setX(point.x);
                setY(point.y);
                setRotation(-(point.x - x) / (getMeasuredWidth() / 5));
            }
        });
        animator.start();
    }

    private void removeAnim() {
        if (isRunningAnim) return;
        isRunningAnim = true;
        Point endPoint = new Point();
        switch (direction) {
            case LEFT_SLIDE:
                endPoint.x = (int) (x - getWidth());
                endPoint.y = 0;
                break;
            case RIGHT_SLIDE:
                endPoint.x = (int) (x + getWidth());
                endPoint.y = 0;
                break;
            case TOP_SLIDE:
                endPoint.x = (int) x;
                endPoint.y = (int) (y - getHeight());
                break;
            case BOTTOM_SLIDE:
                break;
            default:
                break;
        }
        ValueAnimator animator = ValueAnimator.ofObject(new BesselTypeEvaluator(), new Point((int) animStartX, (int) animStartY), endPoint);
        animator.setDuration(200);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isRunningAnim = false;
                ViewGroup viewGroup = (ViewGroup) getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(SlideLayout.this);
                }
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                setX(point.x);
                setY(point.y);
                switch (direction) {
                    case LEFT_SLIDE:
                        setRotation((point.x - x + getWidth()) / (getMeasuredWidth() / 5));
                        break;
                    case RIGHT_SLIDE:
                        setRotation(-(point.x - x - getWidth()) / (getMeasuredWidth() / 5));
                        break;
                    case TOP_SLIDE:
                        break;
                    case BOTTOM_SLIDE:
                        break;
                    default:
                        break;
                }
            }
        });
        animator.start();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            x = getX();
            y = getY();
            parent = (ViewGroup) getParent();
            if (parent instanceof SlidePageView) {
                pageView = (SlidePageView) parent;
            }
            if (pageView != null && pageView.getChildCount() > 0) {
                parentChildView = pageView.getChildAt(pageView.getChildCount() - 1);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ivNearbyPeople = null;
        pageView = null;
        parent = null;
        parentChildView = null;
        downX = 0;
        downY = 0;
        animStartX = 0;
        animStartY = 0;
        x = 0;
        y = 0;
        isRunningAnim = false;
    }
}
