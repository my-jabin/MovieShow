package com.jiujiu.movieShow.ui.movie.movieDetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.jiujiu.movieShow.data.model.Movie;
import com.jiujiu.movieShow.data.remote.TmdbApiService;
import com.jiujiu.movieShow.ui.base.BaseViewModel;
import com.jiujiu.movieShow.util.ErrorUtils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class MovieDetailActivityViewModel extends BaseViewModel {

    private static final String TAG = "MovieDetailActivityView";

    private TmdbApiService tmdbApiService;

    private MutableLiveData<Movie> movieLiveData = new MutableLiveData<>();

    @Inject
    public MovieDetailActivityViewModel(TmdbApiService apiService) {
        tmdbApiService = apiService;
    }

    public void loadMovieById(int id) {
        Disposable d = this.tmdbApiService.getMovie(id)
                .subscribe(movie -> {
                    movieLiveData.postValue(movie);
                }, error -> {
                    postErrorMessage(ErrorUtils.getHttpErrorMessage(error));
                });
        getCompositeDisposable().add(d);
    }

    public LiveData<Movie> getMovieLiveData() {
        return movieLiveData;
    }
}
