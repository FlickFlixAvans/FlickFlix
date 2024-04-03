package com.example.flickflix.data.response;

import com.example.flickflix.model.Genre;
import com.example.flickflix.model.Video;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {
    @SerializedName("results")
    private List<Video> videos;

    public List<Video> getVideos(int movieId) {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
