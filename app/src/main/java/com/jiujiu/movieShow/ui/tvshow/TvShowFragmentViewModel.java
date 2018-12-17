package com.jiujiu.movieShow.ui.tvshow;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.jiujiu.movieShow.data.model.TvShow;
import com.jiujiu.movieShow.data.remote.TmdbApiService;
import com.jiujiu.movieShow.ui.base.BaseViewModel;
import com.jiujiu.movieShow.util.ErrorUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class TvShowFragmentViewModel extends BaseViewModel {

    private TmdbApiService tmdbApiService;
    private MutableLiveData<List<TvShow>> airingTodayList = new MutableLiveData<>();
    private MutableLiveData<List<TvShow>> popularTvShows = new MutableLiveData<>();
    private MutableLiveData<List<TvShow>> topRatedTvShows = new MutableLiveData<>();
    private MutableLiveData<List<TvShow>> onTheAirTvShows = new MutableLiveData<>();

    @Inject
    public TvShowFragmentViewModel(TmdbApiService tmdbApiService) {
        super();
        this.tmdbApiService = tmdbApiService;
    }

    public void loadAiringToday(){
        Disposable d = this.tmdbApiService.airingTodayTvShows()
                .subscribe(response -> {
                    airingTodayList.postValue(response.getTvShows());
                }, error -> {
                    postErrorMessage(ErrorUtils.getHttpErrorMessage(error));
                });
        getCompositeDisposable().add(d);
    }

    public void loadPopularTvShow(){
        Disposable d = this.tmdbApiService.popularTvShows()
                .subscribe(response -> {
                    popularTvShows.postValue(response.getTvShows());
                }, error -> {
                    postErrorMessage(ErrorUtils.getHttpErrorMessage(error));
                });
        getCompositeDisposable().add(d);
    }

    public void loadTopRatedTvShow(){
        Disposable d = this.tmdbApiService.topRatedTvShows()
                .subscribe(response -> {
                    topRatedTvShows.postValue(response.getTvShows());
                }, error -> {
                    postErrorMessage(ErrorUtils.getHttpErrorMessage(error));
                });
        getCompositeDisposable().add(d);
    }

    public void loadOnTheAirTvShow(){
        Disposable d = this.tmdbApiService.onTheAirTvShows()
                .subscribe(response -> {
                    onTheAirTvShows.postValue(response.getTvShows());
                }, error -> {
                    postErrorMessage(ErrorUtils.getHttpErrorMessage(error));
                });
        getCompositeDisposable().add(d);
    }


    public LiveData<List<TvShow>> getAiringTodayLiveData(){
        return this.airingTodayList;
    }

    public LiveData<List<TvShow>> getPopularTvShowsLiveData() {
        return popularTvShows;
    }

    public LiveData<List<TvShow>> getTopRatedTvShowsLiveData() {
        return topRatedTvShows;
    }

    public LiveData<List<TvShow>> getOnTheAirTvShowsLiveData() {
        return onTheAirTvShows;
    }
}
