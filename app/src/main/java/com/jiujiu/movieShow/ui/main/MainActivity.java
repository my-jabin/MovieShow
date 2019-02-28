package com.jiujiu.movieShow.ui.main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jiujiu.movieShow.BR;
import com.jiujiu.movieShow.R;
import com.jiujiu.movieShow.databinding.ActivityMainBinding;
import com.jiujiu.movieShow.ui.base.BaseActivity;
import com.jiujiu.movieShow.ui.movie.MoviesFragment;
import com.jiujiu.movieShow.ui.movie.movieDetail.MovieDetailActivity;
import com.jiujiu.movieShow.ui.tvshow.TvShowFragment;
import com.jiujiu.movieShow.ui.tvshow.tvShowDetail.TvShowDetailActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.jiujiu.movieShow.ui.movie.movieDetail.MovieDetailActivity.MOVIEID;
import static com.jiujiu.movieShow.ui.tvshow.tvShowDetail.TvShowDetailActivity.TVSHOWID;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel> implements HasSupportFragmentInjector {

    private static final String TAG = "MainActivity";
    public static final String TAG_MOVIES = "tag_movies";
    public static final String TAG_TVSHOWS = "tag_tv_shows";
    public static final String TAG_CELEBRITY = "tag_celebrity";

    @Inject
    ViewModelProvider.Factory factory;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupLayout();
        showFragment(TAG_MOVIES);
        setupViewModel();
    }

    private void setupViewModel() {
        getViewModel().getOpenMovieDetailEvent().observe(this,movieId -> {
            Intent intent = new Intent(this,MovieDetailActivity.class);
            intent.putExtra(MOVIEID,movieId);
            startActivity(intent);
        });

        getViewModel().getOpenTvshowDetailEvent().observe(this, tvshowId -> {
            Intent intent = new Intent(this, TvShowDetailActivity.class);
            intent.putExtra(TVSHOWID, tvshowId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupLayout() {
        Toolbar toolbar = getBinding().toolbar;
        setSupportActionBar(toolbar);

        getBinding().navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    showFragment(TAG_MOVIES);
                    return true;
                case R.id.navigation_tv_show:
                    showFragment(TAG_TVSHOWS);
                    return true;
                case R.id.navigation_people:
                    Toast.makeText(this, "notifications", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return true;
        });
    }

    private void showFragment(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment movieFrag = fragmentManager.findFragmentByTag(TAG_MOVIES);
        Fragment tvshowFrag = fragmentManager.findFragmentByTag(TAG_TVSHOWS);

        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        switch (tag) {
            case TAG_MOVIES:
                if (movieFrag == null) {
                    movieFrag = MoviesFragment.getInstance();
                    fragmentTransaction.add(R.id.container, movieFrag, TAG_MOVIES);
                }
                if(tvshowFrag != null){
                    fragmentTransaction.hide(tvshowFrag);
                }
                fragmentTransaction.show(movieFrag);
                break;
            case TAG_TVSHOWS:
                if (tvshowFrag == null) {
                    tvshowFrag = TvShowFragment.getInstance();
                    fragmentTransaction.add(R.id.container, tvshowFrag, TAG_TVSHOWS);
                }
                if(movieFrag != null){
                    fragmentTransaction.hide(movieFrag);
                }
                fragmentTransaction.show(tvshowFrag);
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextChange: submit" + query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected MainActivityViewModel generateViewModel() {
        return ViewModelProviders.of(this, factory).get(MainActivityViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public int getBindingVariableId() {
        return BR.viewModel;
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    public void openMovieDetailActivity(Integer movieId){
        getViewModel().openMovieDetailActivity(movieId);
    }

    public void openTvShowDetailActivity(Integer tvshowId) {
        getViewModel().openTvshowDetailActivity(tvshowId);
    }
}
