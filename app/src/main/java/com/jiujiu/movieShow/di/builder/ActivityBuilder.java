package com.jiujiu.movieShow.di.builder;

import com.jiujiu.movieShow.di.module.MainActivityModule;
import com.jiujiu.movieShow.ui.main.MainActivity;
import com.jiujiu.movieShow.ui.movie.movieDetail.MovieDetailActivity;
import com.jiujiu.movieShow.ui.tvshow.tvShowDetail.TvShowDetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    //    @ContributesAndroidInjector(modules = SplashActivityModule.class)
//    @ContributesAndroidInjector()
//    abstract SplashActivity bindsSplashActivity();

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindsMainActivity();

    @ContributesAndroidInjector()
    abstract MovieDetailActivity bindsMovieDetailActivity();

    @ContributesAndroidInjector()
    abstract TvShowDetailActivity bindsTvshowDetailActivity();

}
