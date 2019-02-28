package com.jiujiu.movieShow.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.jiujiu.movieShow.di.ViewModelFactory;
import com.jiujiu.movieShow.di.ViewModelKey;
import com.jiujiu.movieShow.ui.main.MainActivityViewModel;
import com.jiujiu.movieShow.ui.movie.MoviesFragmentViewModel;
import com.jiujiu.movieShow.ui.movie.movieDetail.MovieDetailActivityViewModel;
import com.jiujiu.movieShow.ui.tvshow.TvShowFragmentViewModel;
import com.jiujiu.movieShow.ui.tvshow.tvShowDetail.TvShowDetailActivityViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    abstract ViewModel bindsMainActivityViewModel(MainActivityViewModel mainActivityViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(MoviesFragmentViewModel.class)
    abstract ViewModel bindsMoviesFragmentViewModel(MoviesFragmentViewModel moviesFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TvShowFragmentViewModel.class)
    abstract ViewModel bindsTvShowFragmentViewModel(TvShowFragmentViewModel tvShowFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailActivityViewModel.class)
    abstract ViewModel bindsMovieDetailActivityViewModel(MovieDetailActivityViewModel movieDetailActivityViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(TvShowDetailActivityViewModel.class)
    abstract ViewModel bindTvshowDetailActivityViewModel(TvShowDetailActivityViewModel tvShowDetailActivityViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory factory);
}
