package com.jiujiu.movieShow.ui.comman;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jiujiu.movieShow.R;
import com.jiujiu.movieShow.data.model.Movie;
import com.jiujiu.movieShow.data.model.TvShow;
import com.jiujiu.movieShow.data.model.vo.Media;
import com.jiujiu.movieShow.data.remote.API;
import com.jiujiu.movieShow.databinding.CirclePageBinding;
import com.jiujiu.movieShow.util.GlideUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CirclePagerAdapater<T extends Media> extends PagerAdapter {
    private static final String TAG = "CirclePagerAdapater";

    private List<T> data;
    private Context mContext;
    private CirclePageBinding mBinding;
    private int size = 0;

    private int previousState;
    private int currentState;

    public CirclePagerAdapater(Context context) {
        mContext = context;
    }

    public void setData(List<T> playings) {
        data = playings;
        size = playings.size() + 2;
        notifyDataSetChanged();
    }

    public int getItemId(int position) {
        if (data.get(position) != null) {
            return data.get(position).getId();
        } else {
            throw new IllegalArgumentException("Cannot access data in position " + position);
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final T media;
        if (position == 0) {
            media = data.get(data.size() - 1);
        } else if (position == size - 1) {
            media = data.get(0);
        } else {
            media = data.get(position - 1);
        }

        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.circle_page, container, false);
        mBinding.moiveTitle.setText(media.getTitle());

        Glide.with(mContext)
                .asBitmap()
                .load(API.getBackdropPath(media.getBackDropPath()))
                .apply(GlideUtils.imageRequestOptions())
                .into(new BitmapImageViewTarget(mBinding.movieBackdrop) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        super.onResourceReady(resource, transition);
                        Palette.from(resource)
                                .generate(palette -> setBackgroundColor(palette, mBinding));
                    }
                });

        container.addView(mBinding.getRoot());

        ((ViewPager) container).addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                ViewPager pager = ((ViewPager) container);
                int currentPage = pager.getCurrentItem();
                if (currentPage == 0 || currentPage == size - 1) {
                    previousState = currentState;
                    currentState = state;
                    if (!pager.isFakeDragging()) {
                        if (previousState == ViewPager.SCROLL_STATE_SETTLING && currentState == ViewPager.SCROLL_STATE_IDLE) {
                            pager.setCurrentItem(currentPage == 0 ? size - 2 : 1, false);
                        }
                    } else {
                        if (previousState == ViewPager.SCROLL_STATE_IDLE && currentState == ViewPager.SCROLL_STATE_IDLE) {
                            pager.setCurrentItem(currentPage == 0 ? size - 2 : 1, false);
                        }
                    }
                }
            }
        });

        return mBinding.getRoot();
    }

    private void setBackgroundColor(Palette palette, CirclePageBinding binding) {
        Palette.Swatch swatch = palette.getDarkVibrantSwatch();
        int backgroundColor = ContextCompat.getColor(mContext, R.color.navy_blue);
        int textColor = ContextCompat.getColor(mContext, android.R.color.white);
        if (swatch != null) {
            backgroundColor = swatch.getRgb();
            textColor = swatch.getBodyTextColor();
        }
        binding.movieTitleBackground.setBackgroundColor(backgroundColor);
        binding.moiveTitle.setTextColor(textColor);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : size;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
