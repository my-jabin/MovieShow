package com.jiujiu.movieShow.ui.movie.movieDetail;

import android.app.Dialog;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jiujiu.movieShow.BR;
import com.jiujiu.movieShow.R;
import com.jiujiu.movieShow.data.model.Movie;
import com.jiujiu.movieShow.data.remote.API;
import com.jiujiu.movieShow.databinding.ActivityMovieDetailBinding;
import com.jiujiu.movieShow.ui.base.BaseActivity;
import com.jiujiu.movieShow.util.GlideUtils;

import javax.inject.Inject;

public class MovieDetailActivity extends BaseActivity<ActivityMovieDetailBinding, MovieDetailActivityViewModel> {
    private static final String TAG = "MovieDetailActivity";
    public static final String MOVIEID = "movie_id";


    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(getBinding().toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        int movieId = getIntent().getIntExtra(MOVIEID, 0);
        getViewModel().loadMovieById(movieId);
    }


    private void setupViewModel() {
        getViewModel().getMovieLiveData().observe(this, movie -> {
            fillupViews(movie);
        });
    }


    private void fillupViews(Movie movie) {
        getBinding().tvMovieTitle.setText(movie.getTitle());

        Glide.with(this)
                .asBitmap()
                .load(API.getBackdropPath(movie.getBackDropPath()))
                .apply(GlideUtils.imageRequestOptions())
                .into(getBinding().movieBackdrop);

        getBinding().movieVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
        getBinding().tvTotalVote.setText(String.valueOf(movie.getVoteCount()));
        getBinding().tvMovieReleaseDate.setText(movie.getReleaseDate());
        getBinding().tvMovieOriginLanguage.setText(movie.getOriginalLanguage());
        getBinding().tvMovieStatus.setText(movie.getStatus());
        getBinding().tvMovieRuntime.setText( movie.getRuntime() + " min");
        getBinding().tvOverviewContent.setText(movie.getOverview());
        getBinding().tvTagLineContent.setText(movie.getTagline());

        GenreRecyclerAdapter adapter = new GenreRecyclerAdapter();
        getBinding().genresRecycler.setAdapter(adapter);
        adapter.setGenres(movie.getGenres());
    }

    @Override
    protected MovieDetailActivityViewModel generateViewModel() {
        return ViewModelProviders.of(this, factory).get(MovieDetailActivityViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    public int getBindingVariableId() {
        return BR.viewModel;
    }
}
