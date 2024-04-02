package com.example.flickflix.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.flickflix.data.repository.MovieRepository;
import com.example.flickflix.data.response.MovieResponse;

public class MovieViewModel extends ViewModel {
    private final MovieRepository repository;

    public MovieViewModel() {
        repository = new MovieRepository();
    }

    public LiveData<MovieResponse> getNowPlayingMovies(Integer page) {
        return repository.getNowPlayingMovies(page);
    }
}
