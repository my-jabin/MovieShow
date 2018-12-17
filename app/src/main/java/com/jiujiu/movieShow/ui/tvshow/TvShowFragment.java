package com.jiujiu.movieShow.ui.tvshow;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiujiu.movieShow.BR;
import com.jiujiu.movieShow.R;
import com.jiujiu.movieShow.data.model.TvShow;
import com.jiujiu.movieShow.databinding.TvShowFragBinding;
import com.jiujiu.movieShow.ui.base.BaseFragment;
import com.jiujiu.movieShow.ui.comman.AutoSwipPageChangeListener;
import com.jiujiu.movieShow.ui.comman.CirclePagerAdapater;
import com.jiujiu.movieShow.ui.comman.MediaRecyclerAdapter;

import javax.inject.Inject;

public class TvShowFragment extends BaseFragment<TvShowFragBinding, TvShowFragmentViewModel> {
    private static final String TAG = "TvShowFragment";
    @Inject
    ViewModelProvider.Factory factory;

    private CirclePagerAdapater<TvShow> mPagerAdapter;
    private MediaRecyclerAdapter<TvShow> mPopularAdapter;
    private MediaRecyclerAdapter<TvShow> mTopRatedAdapter;
    private MediaRecyclerAdapter<TvShow> mOnTheAirAdapter;

    public static TvShowFragment getInstance() {
        Log.d(TAG, "getInstance: create a new tv show fragment");
        TvShowFragment fragment = new TvShowFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        mPagerAdapter = new CirclePagerAdapater<>(getContext());
        getBinding().tvShowViewPager.setAdapter(mPagerAdapter);
//        getBinding().tvShowViewPager.addOnPageChangeListener(new AutoSwipPageChangeListener(getBinding().tvShowViewPager));
        getBinding().tvShowViewPager.setOnPageClickListener(itemId -> {
            Log.d(TAG, "setupRecyclerView: item id = " + itemId);
        });

        mPopularAdapter = new MediaRecyclerAdapter<>();
        getBinding().popularRecycler.setHasFixedSize(true);
        getBinding().popularRecycler.setAdapter(mPopularAdapter);

        mTopRatedAdapter = new MediaRecyclerAdapter<>();
        getBinding().topRatedRecycler.setHasFixedSize(true);
        getBinding().topRatedRecycler.setAdapter(mTopRatedAdapter);

        mOnTheAirAdapter = new MediaRecyclerAdapter<>();
        getBinding().onTheAirRecycler.setHasFixedSize(true);
        getBinding().onTheAirRecycler.setAdapter(mOnTheAirAdapter);

    }

    private void setupViewModel() {

        getViewModel().getErrorMessage().observe(this,o -> {
            Log.e(TAG, "setupViewModel: error message: " + o);
        });

        getViewModel().getAiringTodayLiveData().observe(this, tvShows -> {
            if (mPagerAdapter != null && tvShows != null && tvShows.size() != 0) {
                Log.d(TAG, "setupViewModel: tv shows = " + tvShows.size());
                mPagerAdapter.setData(tvShows);
                getBinding().tvShowViewPager.setCurrentItem(1);
            }
        });

        getViewModel().getPopularTvShowsLiveData().observe(this, tvShows -> {
            if (mPopularAdapter != null && tvShows != null && tvShows.size() != 0) {
                mPopularAdapter.setData(tvShows);
            }
        });

        getViewModel().getTopRatedTvShowsLiveData().observe(this, tvShows -> {
            if (mTopRatedAdapter != null && tvShows != null && tvShows.size() != 0) {
                mTopRatedAdapter.setData(tvShows);
            }
        });

        getViewModel().getOnTheAirTvShowsLiveData().observe(this, tvShows -> {
            if (mOnTheAirAdapter != null && tvShows != null && tvShows.size() != 0) {
                mOnTheAirAdapter.setData(tvShows);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViewModel().loadAiringToday();
        getViewModel().loadPopularTvShow();
        getViewModel().loadTopRatedTvShow();
        getViewModel().loadOnTheAirTvShow();
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tv_show_frag;
    }

    @Override
    protected TvShowFragmentViewModel generateViewmodel() {
        return ViewModelProviders.of(this, factory).get(TvShowFragmentViewModel.class);
    }

    public void onMovieClickListener() {

    }

    public void onTvShowClickListener() {
        Log.d(TAG, "onTvShowClickListener: tv show click");
    }
}
