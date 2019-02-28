package com.jiujiu.movieShow.ui.tvshow.tvShowDetail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.jiujiu.movieShow.BR;
import com.jiujiu.movieShow.R;
import com.jiujiu.movieShow.data.model.TvShow;
import com.jiujiu.movieShow.data.remote.API;
import com.jiujiu.movieShow.databinding.ActivityTvshowDetailBinding;
import com.jiujiu.movieShow.ui.base.BaseActivity;
import com.jiujiu.movieShow.util.AppUtils;
import com.jiujiu.movieShow.util.GlideUtils;

import javax.inject.Inject;

public class TvShowDetailActivity extends BaseActivity<ActivityTvshowDetailBinding, TvShowDetailActivityViewModel> {
    private static final String TAG = "TvShowDetailActivity";
    public static final String TVSHOWID = "tvshow_id";

    @Inject
    ViewModelProvider.Factory factory;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        setupToolbar();
    }


    @Override
    protected void onStart() {
        super.onStart();
        int tvshowId = getIntent().getIntExtra(TVSHOWID, 0);
        Log.d(TAG, "onStart: tv show id = " + tvshowId);
        getViewModel().loadTvShowById(tvshowId);
    }

    private void setupToolbar() {
        setSupportActionBar(getBinding().toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    private void setupViewModel() {

        getViewModel().getErrorMessage().observe(this, error -> {
            Log.e(TAG, "error message: " + error);
        });

        getViewModel().getTvshowLiveData().observe(this, tvShow -> {
            Log.d(TAG, "setupViewModel: ");
            fillupViews(tvShow);
        });
    }

    private void fillupViews(TvShow tvShow) {
        Log.d(TAG, "fillupViews: ");
        //getBinding().collapsingToolbar.setTitle(tvShow.getName());

        Glide.with(this)
                .asBitmap()
                .load(API.getBackdropPath(tvShow.getBackDropPath()))
                .apply(GlideUtils.imageRequestOptions())
                .into(getBinding().ivTvshowBackground);

        Glide.with(this)
                .asBitmap()
                .load(API.getPosterW154Path(tvShow.getPosterPath()))
                .apply(GlideUtils.imageRequestOptions())
                .into(getBinding().tvTvshowPoster);

        getBinding().tvTvshowName.setText(tvShow.getName());
        getBinding().tvTvshowStatus.setText(tvShow.getStatus());
        getBinding().tvTvshowVoteAve.setText(String.valueOf(tvShow.getAverageVote()));
        int year = AppUtils.getYearFromString(tvShow.getFirstAirDate());
        getBinding().tvFirstYear.setText(year > 0 ? String.valueOf(year) : "");
    }

    @Override
    protected TvShowDetailActivityViewModel generateViewModel() {
        return ViewModelProviders.of(this, factory).get(TvShowDetailActivityViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tvshow_detail;
    }

    @Override
    public int getBindingVariableId() {
        return BR.viewModel;
    }
}
