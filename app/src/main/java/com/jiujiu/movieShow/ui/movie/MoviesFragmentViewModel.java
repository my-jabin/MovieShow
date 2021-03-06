package com.jiujiu.movieShow.ui.movie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.jiujiu.movieShow.data.model.Movie;
import com.jiujiu.movieShow.data.remote.TmdbApiService;
import com.jiujiu.movieShow.ui.base.BaseViewModel;
import com.jiujiu.movieShow.util.ErrorUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class MoviesFragmentViewModel extends BaseViewModel {

    private static final String TAG = "MoviesFragmentViewModel";
    private TmdbApiService tmdbApiService;
    private MutableLiveData<List<Movie>> pupularMovieLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> nowplayingMoviesLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> topRatedMoviesLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> upcomingMoviesLiveData = new MutableLiveData<>();

    @Inject
    public MoviesFragmentViewModel(TmdbApiService service) {
        super();
        this.tmdbApiService = service;
    }

    public void loadTopRatedMovies() {
        Disposable d = this.tmdbApiService.topRatedMovies()
                .subscribe(response -> {
                    topRatedMoviesLiveData.postValue(response.getMovies());
                }, error -> {
                    postErrorMessage(ErrorUtils.getHttpErrorMessage(error));
                });
        getCompositeDisposable().add(d);
    }

    public void loadUpcomingMovies() {
        Disposable d = this.tmdbApiService.upcomingMovies()
                .subscribe(response -> {
                    upcomingMoviesLiveData.postValue(response.getMovies());
                }, error -> {
                    postErrorMessage(ErrorUtils.getHttpErrorMessage(error));
                });
        getCompositeDisposable().add(d);
    }


    public void loadNowPlayingMovies() {
        Disposable d = this.tmdbApiService.nowPlayingMovies()
                .subscribe(response -> {
                    nowplayingMoviesLiveData.postValue(response.getMovies());
                }, error -> {
                    postErrorMessage(ErrorUtils.getHttpErrorMessage(error));
                });
        getCompositeDisposable().add(d);
    }

    public void loadPopularMovie() {
        // convert apiresponse to resource
        Disposable d = this.tmdbApiService.popularMovies()
//                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    pupularMovieLiveData.postValue(response.getMovies());
                    Log.d(TAG, "loadPopularMovie: size" + response.getMovies().size());
                }, error -> {
                    postErrorMessage(ErrorUtils.getHttpErrorMessage(error));
                });
        getCompositeDisposable().add(d);

    }

    public LiveData<List<Movie>> getPopularMovies() {
        return this.pupularMovieLiveData;
    }

    public LiveData<List<Movie>> getNowPlayingMovies() {
        return this.nowplayingMoviesLiveData;
    }

    public LiveData<List<Movie>> getTopRatedMovies() {
        return this.topRatedMoviesLiveData;
    }

    public LiveData<List<Movie>> getUpcomingMovies() {
        return this.upcomingMoviesLiveData;
    }

}
