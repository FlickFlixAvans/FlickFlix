package com.example.flickflix.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flickflix.data.ApiService;
import com.example.flickflix.data.RetrofitClient;
import com.example.flickflix.data.response.RequestTokenResponse;
import com.example.flickflix.data.response.AccessTokenResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationRepository {
    private static final String TAG = AuthenticationRepository.class.getSimpleName();
    private final ApiService apiService;

    public AuthenticationRepository() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<String> getRequestToken(String redirectTo) {
        MutableLiveData<String> requestTokenLiveData = new MutableLiveData<>();
        apiService.createRequestToken(redirectTo).enqueue(new Callback<RequestTokenResponse>() {
            @Override
            public void onResponse(Call<RequestTokenResponse> call, Response<RequestTokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    requestTokenLiveData.setValue(response.body().getRequestToken());
                } else {
                    requestTokenLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<RequestTokenResponse> call, Throwable throwable) {
                Log.i(TAG, "Error during token request: " + throwable.getMessage());

                requestTokenLiveData.setValue(null);
            }
        });

        return requestTokenLiveData;
    }

    public LiveData<AccessTokenResponse> createAccessToken(String requestToken) {
        MutableLiveData<AccessTokenResponse> sessionLiveData = new MutableLiveData<>();
        apiService.createAccessToken(requestToken).enqueue(new Callback<AccessTokenResponse>() {
            @Override
            public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Session created successfully
                    sessionLiveData.setValue(response.body());
                } else {
                    sessionLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<AccessTokenResponse> call, Throwable throwable) {
                Log.i(TAG, "Error during session creation: " + throwable.getMessage());

                sessionLiveData.setValue(null);
            }
        });
        return sessionLiveData;
    }
}
