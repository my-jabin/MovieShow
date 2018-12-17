package com.jiujiu.movieShow.di.module;

import android.app.Application;
import android.content.Context;

import com.jiujiu.movieShow.BuildConfig;
import com.jiujiu.movieShow.data.remote.RequestInterceptor;
import com.jiujiu.movieShow.data.remote.TmdbApiService;
import com.jiujiu.movieShow.di.PreferenceInfo;
import com.jiujiu.movieShow.util.AppConstant;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    public static final int CONNECT_TIMEOUT_IN_MS = 30000;

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstant.PREFERENCENAME;
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(RequestInterceptor requestInterceptor) {
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                .addInterceptor(requestInterceptor)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.TMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    TmdbApiService provideTmdbService(Retrofit retrofit) {
        return retrofit.create(TmdbApiService.class);
    }
}
