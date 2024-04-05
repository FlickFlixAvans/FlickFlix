package com.example.flickflix.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.flickflix.data.repository.AuthenticationRepository;
import com.example.flickflix.data.response.AccessTokenResponse;

import java.util.HashMap;

public class AuthenticationViewModel extends ViewModel {
    private final AuthenticationRepository repository;

    public AuthenticationViewModel() {
        repository = new AuthenticationRepository();
    }

    public LiveData<String> getRequestToken(String redirectTo) {
        return repository.getRequestToken(redirectTo);
    }

    public LiveData<AccessTokenResponse> createAccessToken(String requestToken) {
        return repository.createAccessToken(requestToken);
    }
}
