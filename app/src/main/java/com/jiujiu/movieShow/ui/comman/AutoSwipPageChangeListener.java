package com.jiujiu.movieShow.ui.comman;

import android.animation.ValueAnimator;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import com.jiujiu.movieShow.util.AppConstant;

public class AutoSwipPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
    private static final String TAG = "AutoSwipPageChangeListe";
    private int delay = AppConstant.DELAY_DEFAULT;
    private int duration = AppConstant.DURATION_DEFAULT;

    private ViewPager mPager;
    private ValueAnimator valueAnimator;
    private int previousPage;
    private int currentPage;
    private CirclePagerAdapater mAdapter;
    private float currentOffset;

    public AutoSwipPageChangeListener(ViewPager viewPager) {
        mPager = viewPager;
        mAdapter = (CirclePagerAdapater) mPager.getAdapter();
    }

    @Override
    public void onPageSelected(int position) {
//        Log.d(TAG, "onPageSelected: " + position);
        int pageSize = mAdapter.getCount();
        if (position != 0 && position != pageSize - 1) {
            beginAutoSwip();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 0:idle 1: dragging  2: settling
//        Log.d(TAG, "onPageScrollStateChanged: " + state);
        previousPage = currentPage;
        currentPage = mPager.getCurrentItem();
        if (!mPager.isFakeDragging()) {
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                if (valueAnimator != null && valueAnimator.isStarted()) {
//                    Log.d(TAG, "onPageScrollStateChanged:  value animator canceled");
                    valueAnimator.cancel();
                    valueAnimator.removeAllUpdateListeners();
                }
            } else if (state == ViewPager.SCROLL_STATE_SETTLING && previousPage == currentPage) {
                beginAutoSwip();
            }
        }
    }

    private void beginAutoSwip() {
//        Log.d(TAG, "beginAutoSwip: " + mPager.getCurrentItem());
        float maxOffset = mPager.getWidth();
        valueAnimator = ValueAnimator.ofFloat(0, maxOffset);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setStartDelay(delay);
        valueAnimator.setDuration(duration);

        valueAnimator.addUpdateListener(updatedAnimation -> {
            float animatedValue = (float) updatedAnimation.getAnimatedValue();
            if (!mPager.isFakeDragging()) {
                mPager.beginFakeDrag();
            }
            if (mPager.isFakeDragging()) {
                float drag = -1 * (animatedValue - this.currentOffset);
                mPager.fakeDragBy(drag);
                this.currentOffset = animatedValue;
            }
            if (animatedValue == maxOffset && mPager.isFakeDragging()) {
//                Log.d(TAG, "end fake currentOffset: ");
                mPager.endFakeDrag();
                currentOffset = 0;
            }
        });
        valueAnimator.start();
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
