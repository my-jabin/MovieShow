package com.jiujiu.movieShow.ui.comman;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.AdapterView;

public class AutoCyclerViewPager extends ViewPager {

    private static final String TAG = "AutoCyclerViewPager";
//    private int previousMotion;
    private AutoSwipPageChangeListener mPageChangeListener;
    private OnPageClickListener mOnPageClickListener;

    public AutoCyclerViewPager(@NonNull Context context) {
        super(context);
    }

    public AutoCyclerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        final GestureDetector tapGestureDector = new GestureDetector(context, new TagGestureListener());
        setOnTouchListener((v, event) -> {
            tapGestureDector.onTouchEvent(event);
            return false;
        });
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        boolean result;
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                result = true;
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d(TAG, "onTouchEvent: action ups");
//                if (previousMotion == MotionEvent.ACTION_DOWN) {
//                    Log.d(TAG, "click up : perform click");
//                    if (mOnPageClickListener != null) {
//                        mOnPageClickListener.onPageClicked(((CirclePagerAdapater) getAdapter()).getItemId(getCurrentItem() - 1));
//                    }
//                    result = true;
//                } else {
//                    result = super.onTouchEvent(event);
//                }
//                break;
//            case MotionEvent.ACTION_MOVE:
//                result = super.onTouchEvent(event);
//                break;
//            default:
//                result = super.onTouchEvent(event);
//                break;
//        }
//        previousMotion = event.getAction();
//        return result;
//    }


    @Override
    public void setAdapter(@Nullable PagerAdapter adapter) {
        if (!(adapter instanceof CirclePagerAdapater)) {
            throw new IllegalArgumentException(adapter.getClass().getName() + " is not a subclass of " + CirclePagerAdapater.class.getName());
        }
        super.setAdapter(adapter);
        mPageChangeListener = new AutoSwipPageChangeListener(this);
        this.addOnPageChangeListener(mPageChangeListener);
    }

    public void setOnPageClickListener(OnPageClickListener listener) {
        mOnPageClickListener = listener;
    }

    @Override
    public void clearOnPageChangeListeners() {
        super.clearOnPageChangeListeners();
        this.addOnPageChangeListener(mPageChangeListener);
    }

    public interface OnPageClickListener {
        void onPageClicked(int itemId);
    }

    private class TagGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (mOnPageClickListener != null) {
                mOnPageClickListener.onPageClicked(((CirclePagerAdapater) getAdapter()).getItemId(getCurrentItem() - 1));
            }
            return true;
        }
    }
}
