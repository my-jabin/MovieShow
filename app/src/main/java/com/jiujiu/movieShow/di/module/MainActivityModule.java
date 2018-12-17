package com.jiujiu.movieShow.di.module;

import com.jiujiu.movieShow.ui.movie.MoviesFragment;
import com.jiujiu.movieShow.ui.tvshow.TvShowFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = MovieFragmentModule.class)
    abstract MoviesFragment bindsMovieFragment();


    @ContributesAndroidInjector(modules = TvShowFragmentModule.class)
    abstract TvShowFragment bindsTvShowsFragment();
}
