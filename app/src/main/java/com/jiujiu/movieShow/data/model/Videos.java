package com.jiujiu.movieShow.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Videos {

    @SerializedName("results")
    @Expose
    private List<Video> results = null;

    private List<Video> trailers = null;

    public List<Video> getVideos() {
        return results;
    }

    public void setVideos(List<Video> results) {
        this.results = results;
    }

    public List<Video> getTrailers() {
        if (this.results == null || this.results.size() == 0) return null;

        if (trailers == null || trailers.size() == 0) {
            trailers = new ArrayList<>();
            for (Video v : results) {
                if ("Trailer".equalsIgnoreCase(v.getType().trim())) {
                    trailers.add(v);
                }
            }
        }
        return this.trailers;
    }

}