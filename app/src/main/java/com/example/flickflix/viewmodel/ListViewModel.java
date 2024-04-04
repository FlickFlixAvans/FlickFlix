package com.example.flickflix.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.flickflix.data.repository.ListRepository;
import com.example.flickflix.data.response.ListResponse;

public class ListViewModel extends ViewModel {
    private final ListRepository repository;

    public ListViewModel() {
        repository = new ListRepository();
    }

    public LiveData<ListResponse> getLists(String accountId, int page, String authorization) {
        return repository.getLists(accountId, page, authorization);
    }

    public LiveData<Boolean> createList(String name, String description, String authorization) {
        return repository.createList(name, description, authorization);
    }
}