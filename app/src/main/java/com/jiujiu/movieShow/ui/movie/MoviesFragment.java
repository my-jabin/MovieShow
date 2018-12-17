package com.jiujiu.movieShow.ui.movie;

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
import com.jiujiu.movieShow.data.model.Movie;
import com.jiujiu.movieShow.databinding.MoviesFragBinding;
import com.jiujiu.movieShow.ui.base.BaseFragment;
import com.jiujiu.movieShow.ui.comman.CirclePagerAdapater;
import com.jiujiu.movieShow.ui.comman.MediaClickListener;
import com.jiujiu.movieShow.ui.comman.MediaRecyclerAdapter;
import com.jiujiu.movieShow.ui.main.MainActivity;

import javax.inject.Inject;

public class MoviesFragment extends BaseFragment<MoviesFragBinding, MoviesFragmentViewModel> {

    // todo: handle no internet situation and error
    // todo: back from movieDetailActivity the auto swipe is not working。 Lifecycler problem
    private static final String TAG = "MoviesFragment";
    @Inject
    ViewModelProvider.Factory factory;

    //todo inject pageradapter
    private CirclePagerAdapater<Movie> mPagerAdapter;
    private MediaRecyclerAdapter<Movie> mPopularMoviesAdapter;
    private MediaRecyclerAdapter<Movie> mTopRatedMoviesAdapter;
    private MediaRecyclerAdapter<Movie> mUpcomingMoviesAdapter;

    public static MoviesFragment getInstance() {
        Log.d(TAG, "getInstance: create a new movies fragment");
        MoviesFragment fragment = new MoviesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
    }


    private void setupViewModel() {
        getViewModel().getPopularMovies().observe(this, movies -> {
            if (mPopularMoviesAdapter != null && movies != null && movies.size() != 0) {
                mPopularMoviesAdapter.setMedia(movies);
            }
        });

        getViewModel().getNowPlayingMovies().observe(this, movies -> {
            if (mPagerAdapter != null && movies != null && movies.size() != 0) {
                mPagerAdapter.setData(movies);
                getBinding().movieViewPager.setCurrentItem(1);
            }
        });

        getViewModel().getTopRatedMovies().observe(this, movies -> {
            if (mTopRatedMoviesAdapter != null && movies != null && movies.size() != 0) {
                mTopRatedMoviesAdapter.setMedia(movies);
            }
        });

        getViewModel().getUpcomingMovies().observe(this, movies -> {
            if (mUpcomingMoviesAdapter != null && movies != null && movies.size() != 0) {
                mUpcomingMoviesAdapter.setMedia(movies);
            }
        });
    }

    private void setupRecyclerView() {
        mPagerAdapter = new CirclePagerAdapater<>(getContext());
        getBinding().movieViewPager.setAdapter(mPagerAdapter);
        //  changed: comment here. change the view in layout file
//        getBinding().movieViewPager.addOnPageChangeListener(new AutoSwipPageChangeListener(getBinding().movieViewPager));
        getBinding().movieViewPager.setOnPageClickListener(itemId -> {
            Log.d(TAG, "setupRecyclerView: item id = " + itemId);
            ((MainActivity)getActivity()).openMovieDetailActivity(itemId);
        });

        mPopularMoviesAdapter = new MediaRecyclerAdapter<>();
        mPopularMoviesAdapter.setOnMediaClickListener(mediaId -> {
            ((MainActivity)getActivity()).openMovieDetailActivity(mediaId);
        });
        getBinding().popularRecycler.setHasFixedSize(true);
        getBinding().popularRecycler.setAdapter(mPopularMoviesAdapter);

        mTopRatedMoviesAdapter = new MediaRecyclerAdapter<>();
        mTopRatedMoviesAdapter.setOnMediaClickListener(mediaId -> {
            ((MainActivity)getActivity()).openMovieDetailActivity(mediaId);
        });
        getBinding().topRatedRecycler.setHasFixedSize(true);
        getBinding().topRatedRecycler.setAdapter(mTopRatedMoviesAdapter);

        mUpcomingMoviesAdapter = new MediaRecyclerAdapter<>();
        mUpcomingMoviesAdapter.setOnMediaClickListener(mediaId -> {
            ((MainActivity)getActivity()).openMovieDetailActivity(mediaId);
        });
        getBinding().upcomingRecycler.setHasFixedSize(true);
        getBinding().upcomingRecycler.setAdapter(mUpcomingMoviesAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        setupRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViewModel().loadNowPlayingMovies();
        getViewModel().loadPopularMovie();
        getViewModel().loadTopRatedMovies();
        getViewModel().loadUpcomingMovies();
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.movies_frag;
    }

    @Override
    protected MoviesFragmentViewModel generateViewmodel() {
        return ViewModelProviders.of(this, factory).get(MoviesFragmentViewModel.class);
    }

}
