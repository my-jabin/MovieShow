package com.jiujiu.movieShow.ui.tvshow.tvShowDetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.jiujiu.movieShow.data.model.TvShow;
import com.jiujiu.movieShow.data.remote.TmdbApiService;
import com.jiujiu.movieShow.ui.base.BaseViewModel;
import com.jiujiu.movieShow.util.ErrorUtils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class TvShowDetailActivityViewModel extends BaseViewModel {

    private static final String TAG = "TvShowDetailActivityVie";

    private TmdbApiService tmdbApiService;
    private MutableLiveData<TvShow> tvShowLiveData = new MutableLiveData<>();

    @Inject
    public TvShowDetailActivityViewModel(TmdbApiService apiService) {
        tmdbApiService = apiService;
    }


    public void loadTvShowById(int id) {
        Disposable d = tmdbApiService.getTvshowById(id)
                .subscribe(tvShow -> {
                    Log.d(TAG, "loadTvShowById: " + id);
                    tvShowLiveData.postValue(tvShow);
                }, throwable -> {
                    Log.d(TAG, "loadTvShowById: error");
                    throwable.printStackTrace();
                    postErrorMessage(ErrorUtils.getHttpErrorMessage(throwable));
                });
        getCompositeDisposable().add(d);
    }

    public LiveData<TvShow> getTvshowLiveData() {
        return this.tvShowLiveData;
    }
}
