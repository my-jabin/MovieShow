package com.jiujiu.movieShow.data.remote;

public class API {

    public static final String BASE_POSTER_PATH = "http://image.tmdb.org/t/p/";
    public static final String BASR_BACKDROP_PATH = "http://image.tmdb.org/t/p/w780";

    public static String getPosterW500Path(String posterPath) {
        return BASE_POSTER_PATH +"w500/"+ posterPath;
    }

    public static String getPosterW92Path(String posterPath) {
        return BASE_POSTER_PATH +"w92/"+ posterPath;
    }

    public static String getPosterW300Path(String posterPath) {
        return BASE_POSTER_PATH +"w300/"+ posterPath;
    }

    public static String getPosterW154Path(String posterPath) {
        return BASE_POSTER_PATH +"w154/"+ posterPath;
    }

    public static String getBackdropPath(String path){
        return BASR_BACKDROP_PATH + path;
    }
}
