package com.example.flickflix.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flickflix.data.ApiService;
import com.example.flickflix.data.RetrofitClient;
import com.example.flickflix.data.response.GenreResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreRepository {
    private static final String TAG = GenreRepository.class.getSimpleName();
    private final ApiService apiService;

    public GenreRepository() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<GenreResponse> getGenres() {
        MutableLiveData<GenreResponse> genresLiveData = new MutableLiveData<>();
        apiService.getGenres().enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    genresLiveData.setValue(response.body());
                } else {
                    genresLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable throwable) {
                Log.i(TAG, "Error during get genres request: " + throwable.getMessage());

                genresLiveData.setValue(null);
            }
        });

        return genresLiveData;
    }
}
