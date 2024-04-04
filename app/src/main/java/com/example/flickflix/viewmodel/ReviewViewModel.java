package com.example.flickflix.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.flickflix.data.repository.GenreRepository;
import com.example.flickflix.data.repository.ReviewRepository;
import com.example.flickflix.data.repository.VideoRepository;
import com.example.flickflix.data.response.GenreResponse;
import com.example.flickflix.data.response.ReviewResponse;
import com.example.flickflix.data.response.VideoResponse;
import com.example.flickflix.model.Video;

public class ReviewViewModel extends ViewModel {
    private final ReviewRepository repository;

    public ReviewViewModel() {
        repository = new ReviewRepository();
    }

    public LiveData<ReviewResponse> getReview(int movieId) {
        return repository.getReview(movieId);
    }
}
