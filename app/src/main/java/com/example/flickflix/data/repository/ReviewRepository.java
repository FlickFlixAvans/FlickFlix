package com.example.flickflix.data.repository;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flickflix.data.ApiService;
import com.example.flickflix.data.RetrofitClient;
import com.example.flickflix.data.response.ReviewResponse;
import com.example.flickflix.data.response.VideoResponse;
import com.example.flickflix.model.Movie;
import com.example.flickflix.model.Review;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewRepository {
    private static final String TAG = VideoRepository.class.getSimpleName();
    private final ApiService apiService;

    public ReviewRepository() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<ReviewResponse> getReview(int movieId) {
        MutableLiveData<ReviewResponse> reviewsLiveData = new MutableLiveData<>();
        apiService.getReview(movieId).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reviewsLiveData.setValue(response.body());
                } else {
                    reviewsLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable throwable) {
                Log.i(TAG, "Error during get reviews request: " + throwable.getMessage());

                reviewsLiveData.setValue(null);
            }
        });

        return reviewsLiveData;
    }
}
