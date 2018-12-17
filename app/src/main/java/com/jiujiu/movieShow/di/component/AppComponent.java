package com.jiujiu.movieShow.di.component;

import android.app.Application;

import com.jiujiu.movieShow.MovieApp;
import com.jiujiu.movieShow.di.builder.ActivityBuilder;
import com.jiujiu.movieShow.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(MovieApp app);

}
