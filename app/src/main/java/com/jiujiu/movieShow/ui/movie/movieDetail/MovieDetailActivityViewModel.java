package com.jiujiu.movieShow.ui.movie.movieDetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.jiujiu.movieShow.data.model.Cast;
import com.jiujiu.movieShow.data.model.Crew;
import com.jiujiu.movieShow.data.model.Images;
import com.jiujiu.movieShow.data.model.Movie;
import com.jiujiu.movieShow.data.model.Videos;
import com.jiujiu.movieShow.data.remote.TmdbApiService;
import com.jiujiu.movieShow.ui.base.BaseViewModel;
import com.jiujiu.movieShow.util.ErrorUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class MovieDetailActivityViewModel extends BaseViewModel {

    private static final String TAG = "MovieDetailActivityView";

    private TmdbApiService tmdbApiService;

    private MutableLiveData<Movie> movieLiveData = new MutableLiveData<>();
    private MutableLiveData<Images> imagesLiveData = new MutableLiveData<>();
    private MutableLiveData<Videos> videosLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Cast>> castsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Crew>> crewsLiveData = new MutableLiveData<>();

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

    public void loadMovieImages(int id) {
        Log.d(TAG, "loadMovieImages: ");
        Disposable d = this.tmdbApiService.getImagesByMovieId(id)
                .subscribe(images -> {
                    Log.d(TAG, "loadMovieImages: load images finish. size  :" + images.getBackdrops().size());
                    imagesLiveData.postValue(images);
                }, error -> {
                    postErrorMessage(ErrorUtils.getHttpErrorMessage(error));
                });
        getCompositeDisposable().add(d);
    }

    public void loadMovieVideos(int id) {
        Disposable d = this.tmdbApiService.getVideosByMovieId(id)
                .subscribe(videos -> {
                    Log.d(TAG, "loadMovieImages: load images finish. size  :" + videos.getVideos().size());
                    videosLiveData.postValue(videos);
                }, error -> {
                    postErrorMessage(ErrorUtils.getHttpErrorMessage(error));
                });
        getCompositeDisposable().add(d);
    }

    public void loadMovieCredits(int id) {
        Disposable d = this.tmdbApiService.getMovieCredits(id)
                .subscribe(credits -> {
                    Log.d(TAG, "loadMovieImages: load images finish. size  :" + credits.getCast().size());
                    castsLiveData.postValue(credits.getCast());
                    crewsLiveData.postValue(credits.getCrew());
                }, error -> {
                    postErrorMessage(ErrorUtils.getHttpErrorMessage(error));
                });
        getCompositeDisposable().add(d);
    }

    public LiveData<Movie> getMovieLiveData() {
        return movieLiveData;
    }

    public LiveData<Images> getMovieImagesLiveData() {
        return this.imagesLiveData;
    }

    public LiveData<Videos> getMovieVideosLiveData() {
        return this.videosLiveData;
    }

    public LiveData<List<Cast>> getCastsLiveData() {
        return this.castsLiveData;
    }
}
