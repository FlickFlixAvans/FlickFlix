package com.example.flickflix.data.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flickflix.data.ApiService;
import com.example.flickflix.data.RetrofitClient;
import com.example.flickflix.data.response.ListResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRepository {
    private final ApiService apiService;

    public ListRepository() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<ListResponse> getLists(String accountId, int page) {
        MutableLiveData<ListResponse> data = new MutableLiveData<>();
        apiService.getMovieLists(accountId, page).enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e(TAG, "Server error: " + errorBody);
                    } catch (IOException e) {
                        Log.e(TAG, "Error reading error body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {
                Log.e(TAG, "Network error: " + t.getMessage());
            }
        });
        return data;
    }
}
