package com.jiujiu.movieShow.data.remote;

public class API {

    public static final String BASE_POSTER_PATH = "http://image.tmdb.org/t/p/";
    public static final String BASR_BACKDROP_PATH = "http://image.tmdb.org/t/p/w780";
    public static final String YOUTUBE_TRAILER_IMAGE_PATH = "https://img.youtube.com/vi/%s/0.jpg";
    public static final String YOUTUBE_TRAILER_PATH = "https://WWW.youtube.com/watch?v=%s";

    public static String getPosterW500Path(String posterPath) {
        return BASE_POSTER_PATH + "w500/" + posterPath;
    }

    public static String getPosterW92Path(String posterPath) {
        return BASE_POSTER_PATH + "w92/" + posterPath;
    }

    public static String getPosterW300Path(String posterPath) {
        return BASE_POSTER_PATH + "w300/" + posterPath;
    }

    public static String getPosterW154Path(String posterPath) {
        return BASE_POSTER_PATH + "w154/" + posterPath;
    }

    public static String getBackdropPath(String path) {
        return BASR_BACKDROP_PATH + path;
    }

    public static String getYoutubeTrailerImg(String key) {
        return String.format(YOUTUBE_TRAILER_IMAGE_PATH, key);
    }

    public static String getYoutubeTrailerPath(String key) {
        return String.format(YOUTUBE_TRAILER_PATH, key);
    }

}
