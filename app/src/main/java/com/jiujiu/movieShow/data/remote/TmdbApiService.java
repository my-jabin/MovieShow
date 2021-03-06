package com.jiujiu.movieShow.data.remote;

import com.jiujiu.movieShow.data.model.Credits;
import com.jiujiu.movieShow.data.model.DiscoverMovieResponse;
import com.jiujiu.movieShow.data.model.Images;
import com.jiujiu.movieShow.data.model.Movie;
import com.jiujiu.movieShow.data.model.MovieResponse;
import com.jiujiu.movieShow.data.model.TvShow;
import com.jiujiu.movieShow.data.model.TvShowResponse;
import com.jiujiu.movieShow.data.model.Videos;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TmdbApiService {

    @GET("discover/movie?sort_by=popularity.desc")
    Observable<DiscoverMovieResponse> popularMovies();

    @GET("movie/now_playing")
    Observable<MovieResponse> nowPlayingMovies();

    @GET("movie/top_rated")
    Observable<MovieResponse> topRatedMovies();

    @GET("movie/upcoming")
    Observable<MovieResponse> upcomingMovies();

    @GET("tv/airing_today")
    Observable<TvShowResponse> airingTodayTvShows();

    @GET("tv/popular")
    Observable<TvShowResponse> popularTvShows();

    @GET("tv/top_rated")
    Observable<TvShowResponse> topRatedTvShows();

    @GET("tv/on_the_air")
    Observable<TvShowResponse> onTheAirTvShows();

    @GET("movie/{movieId}")
    Observable<Movie> getMovie(@Path("movieId")int id);

    @GET("movie/{movieId}/images?include_image_language=null")
    Observable<Images> getImagesByMovieId(@Path(("movieId")) int id);

    @GET("movie/{movieId}/videos")
    Observable<Videos> getVideosByMovieId(@Path(("movieId")) int id);

    @GET("movie/{movieId}/credits")
    Observable<Credits> getMovieCredits(@Path(("movieId")) int id);

    @GET("tv/{tvId}")
    Observable<TvShow> getTvshowById(@Path(("tvId")) int id);
}
