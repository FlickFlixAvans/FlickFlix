package com.example.flickflix.data.repository;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flickflix.data.ApiService;
import com.example.flickflix.data.RetrofitClient;
import com.example.flickflix.data.response.VideoResponse;
import com.example.flickflix.model.Movie;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoRepository {
    private static final String TAG = VideoRepository.class.getSimpleName();
    private final ApiService apiService;

    public VideoRepository() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<VideoResponse> getVideos(int movieId) {
        MutableLiveData<VideoResponse> videosLiveData = new MutableLiveData<>();
        apiService.getVideos(movieId).enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    videosLiveData.setValue(response.body());
                } else {
                    videosLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable throwable) {
                Log.i(TAG, "Error during get genres request: " + throwable.getMessage());

                videosLiveData.setValue(null);
            }
        });

        return videosLiveData;
    }
}
