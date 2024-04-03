package com.example.flickflix.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flickflix.data.ApiService;
import com.example.flickflix.data.RetrofitClient;
import com.example.flickflix.data.response.MovieResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private static final String TAG = MovieRepository.class.getSimpleName();
    private final ApiService apiService;

    public MovieRepository() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<MovieResponse> getMovies(Integer page, String sortBy, Boolean includeAdult) {
        MutableLiveData<MovieResponse> moviesLiveData = new MutableLiveData<>();
        apiService.getMovies(page, sortBy, includeAdult).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    moviesLiveData.setValue(response.body());
                } else {
                    moviesLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Log.i(TAG, "Error during get now playing movies request: " + throwable.getMessage());

                moviesLiveData.setValue(null);
            }
        });

        return moviesLiveData;
    }
}
