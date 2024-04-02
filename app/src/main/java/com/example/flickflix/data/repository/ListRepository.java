package com.example.flickflix.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flickflix.data.ApiService;
import com.example.flickflix.data.RetrofitClient;
import com.example.flickflix.data.response.ListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRepository {
    private static final String TAG = ListRepository.class.getSimpleName();
    private final ApiService apiService;

    public ListRepository() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<ListResponse> getLists() {
        MutableLiveData<ListResponse> listsLiveData = new MutableLiveData<>();
        apiService.getLists().enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listsLiveData.setValue(response.body());
                } else {
                    listsLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable throwable) {
                Log.i(TAG, "Error during get lists request: " + throwable.getMessage());

                listsLiveData.setValue(null);
            }
        });

        return listsLiveData;
    }
}
