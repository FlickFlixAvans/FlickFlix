package com.example.flickflix.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.flickflix.data.repository.GenreRepository;
import com.example.flickflix.data.response.GenreResponse;

public class GenreViewModel extends ViewModel {
    private final GenreRepository repository;

    public GenreViewModel() {
        repository = new GenreRepository();
    }

    public LiveData<GenreResponse> getGenres() {
        return repository.getGenres();
    }
}
