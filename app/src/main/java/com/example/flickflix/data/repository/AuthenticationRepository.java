package com.example.flickflix.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flickflix.data.ApiService;
import com.example.flickflix.data.RetrofitClient;
import com.example.flickflix.data.response.RequestTokenResponse;
import com.example.flickflix.data.response.SessionResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationRepository {
    private static final String TAG = AuthenticationRepository.class.getSimpleName();
    private final ApiService apiService;

    public AuthenticationRepository() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<String> getRequestToken() {
        MutableLiveData<String> requestTokenLiveData = new MutableLiveData<>();
        apiService.createRequestToken().enqueue(new Callback<RequestTokenResponse>() {
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

    public LiveData<String> createSession(String requestToken) {
        MutableLiveData<String> sessionLiveData = new MutableLiveData<>();
        apiService.createSession(requestToken).enqueue(new Callback<SessionResponse>() {
            @Override
            public void onResponse(Call<SessionResponse> call, Response<SessionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Session created successfully
                    sessionLiveData.setValue(response.body().getSessionId());
                } else {
                    sessionLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<SessionResponse> call, Throwable throwable) {
                Log.i(TAG, "Error during session creation: " + throwable.getMessage());

                sessionLiveData.setValue(null);
            }
        });
        return sessionLiveData;
    }
}
