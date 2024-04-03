package com.example.flickflix.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.flickflix.data.repository.GenreRepository;
import com.example.flickflix.data.repository.VideoRepository;
import com.example.flickflix.data.response.GenreResponse;
import com.example.flickflix.data.response.VideoResponse;
import com.example.flickflix.model.Video;

public class VideoViewModel extends ViewModel {
    private final VideoRepository repository;

    public VideoViewModel() {
        repository = new VideoRepository();
    }

    public LiveData<VideoResponse> getVideos(int movieId) {
        return repository.getVideos(movieId);
    }
}
