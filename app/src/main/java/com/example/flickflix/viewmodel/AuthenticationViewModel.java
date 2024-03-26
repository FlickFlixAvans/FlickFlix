package com.example.flickflix.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.flickflix.data.repository.AuthenticationRepository;

public class AuthenticationViewModel extends ViewModel {
    private final AuthenticationRepository repository;

    public AuthenticationViewModel() {
        repository = new AuthenticationRepository();
    }

    public LiveData<String> getRequestToken() {
        return repository.getRequestToken();
    }

    public LiveData<String> createSession(String requestToken) {
        return repository.createSession(requestToken);
    }
}
