package com.jiujiu.movieShow.ui.movie.movieDetail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.bumptech.glide.Glide;
import com.jiujiu.movieShow.BR;
import com.jiujiu.movieShow.R;
import com.jiujiu.movieShow.data.model.Movie;
import com.jiujiu.movieShow.data.remote.API;
import com.jiujiu.movieShow.databinding.ActivityMovieDetailBinding;
import com.jiujiu.movieShow.ui.base.BaseActivity;
import com.jiujiu.movieShow.ui.movie.movieDetail.VideosRecyclerAdapter.TrailerClickListener;
import com.jiujiu.movieShow.util.GlideUtils;

import javax.inject.Inject;

public class MovieDetailActivity extends BaseActivity<ActivityMovieDetailBinding, MovieDetailActivityViewModel> implements TrailerClickListener {
    private static final String TAG = "MovieDetailActivity";
    public static final String MOVIEID = "movie_id";

    @Inject
    ViewModelProvider.Factory factory;

    private GenreRecyclerAdapter mGenreadapter = new GenreRecyclerAdapter();
    private ImagesRecyclerAdapter mImageAdapter = new ImagesRecyclerAdapter();
    private VideosRecyclerAdapter mVideoAdapter = new VideosRecyclerAdapter(this);
    private CastsRecyclerAdapter mCastsAdapter = new CastsRecyclerAdapter();

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
        getViewModel().loadMovieImages(movieId);
        getViewModel().loadMovieVideos(movieId);
        getViewModel().loadMovieCredits(movieId);

        getBinding().genresRecycler.setHasFixedSize(true);
        getBinding().genresRecycler.setAdapter(mGenreadapter);
        getBinding().rcMovieImages.setHasFixedSize(true);
        getBinding().rcMovieImages.setAdapter(mImageAdapter);
        getBinding().rcMovieVideos.setHasFixedSize(true);
        getBinding().rcMovieVideos.setAdapter(mVideoAdapter);
        getBinding().rcMovieCasts.setHasFixedSize(true);
        getBinding().rcMovieCasts.setAdapter(mCastsAdapter);
    }


    private void setupViewModel() {
        getViewModel().getMovieLiveData().observe(this, movie -> {
            fillupViews(movie);
        });

        getViewModel().getMovieImagesLiveData().observe(this, images -> {
            mImageAdapter.setImages(images);
        });

        getViewModel().getMovieVideosLiveData().observe(this, videos -> {
            mVideoAdapter.setVideos(videos);
        });

        getViewModel().getCastsLiveData().observe(this, casts -> {
            mCastsAdapter.setCasts(casts);
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
        getBinding().tvMovieRuntime.setText(movie.getRuntime() + " min");
        getBinding().tvOverviewContent.setText(movie.getOverview());
        getBinding().tvTagLineContent.setText(movie.getTagline());

        mGenreadapter.setGenres(movie.getGenres());

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

    @Override
    public void onTrailerClickListener(String key) {
        String videoUrl = API.getYoutubeTrailerPath(key);
        Intent playVideoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        startActivity(playVideoIntent);
    }
}
