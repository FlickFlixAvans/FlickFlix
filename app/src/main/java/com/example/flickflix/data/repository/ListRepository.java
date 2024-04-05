package com.example.flickflix.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flickflix.data.ApiService;
import com.example.flickflix.data.RetrofitClient;
import com.example.flickflix.data.response.ListResponse;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRepository {
    private static final String TAG = ListRepository.class.getSimpleName();
    private final ApiService apiService;

    public ListRepository() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<ListResponse> getLists(String accountId, int page, String authorization) {
        MutableLiveData<ListResponse> data = new MutableLiveData<>();
        apiService.getMovieLists(accountId, page, authorization).enqueue(new Callback<ListResponse>() {
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
                Log.e(TAG, "Error during get lists request: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<Boolean> createList(String name, String description, String authorization) {
        MutableLiveData<Boolean> data = new MutableLiveData<>();
        apiService.createMovieList(name, description, authorization).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "Created list!");

                if (response.isSuccessful()) {
                    data.setValue(true);
                } else {
                    data.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.i(TAG, "Error during create list request: " + throwable.getMessage());

                data.setValue(false);
            }
        });
        return data;
    }
}
