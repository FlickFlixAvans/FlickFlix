package com.example.flickflix.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.flickflix.data.repository.ReviewRepository;
import com.example.flickflix.data.response.ReviewResponse;

public class ReviewViewModel extends ViewModel {
    private final ReviewRepository repository;

    public ReviewViewModel() {
        repository = new ReviewRepository();
    }

    public LiveData<ReviewResponse> getReviews(int movieId) {
        return repository.getReviews(movieId);
    }
}
