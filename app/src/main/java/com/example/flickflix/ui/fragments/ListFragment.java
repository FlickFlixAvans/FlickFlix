package com.example.flickflix.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flickflix.data.SharedPreferencesManager;
import com.example.flickflix.model.MovieList;
import com.example.flickflix.databinding.FragmentListBinding;
import com.example.flickflix.ui.adapter.ListAdapter;
import com.example.flickflix.ui.adapter.PaginationScrollListener;
import com.example.flickflix.ui.dialog.CreateListDialog;
import com.example.flickflix.viewmodel.ListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListFragment extends Fragment {
    private FragmentListBinding binding;
    ListViewModel listViewModel;
    ListAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    FloatingActionButton fabAddList;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;
    private int TOTAL_PAGES;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);

        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);

        adapter = new ListAdapter();
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView = binding.rvMovieList;
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        // Register floating action button
        fabAddList = binding.fabAddList;

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Show the Create List dialog
        fabAddList.setOnClickListener(v -> {
            CreateListDialog dialog = new CreateListDialog(getContext(), listViewModel);
            dialog.show();
        });

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // Load the next page
                if (currentPage <= TOTAL_PAGES) {
                    loadNextPage();
                }
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        // Load first page
        loadFirstPage();
    }

    private void loadNextPage() {
        SharedPreferencesManager manager = new SharedPreferencesManager(getContext());
        String accountId = manager.getAccountId();

        // Add the access token to the header
        String accessToken = manager.getAccessToken();
        String authorization = "Bearer " + accessToken;

        listViewModel.getLists(accountId, currentPage, authorization).observe(getViewLifecycleOwner(), listResponse -> {
            adapter.removeLoadingFooter();
            isLoading = false;

            if (listResponse != null && listResponse.getResults() != null && !listResponse.getResults().isEmpty()) {
                TOTAL_PAGES = listResponse.getTotalPages();

                List<MovieList> movieLists = listResponse.getResults(); // Assuming getResults returns List<MovieList>
                adapter.addAll(movieLists);

                if (currentPage != TOTAL_PAGES) {
                    adapter.addLoadingFooter();
                } else {
                    isLastPage = true;
                }
            } else {
                Toast.makeText(getContext(), "Failed to get lists data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFirstPage() {
        adapter.addLoadingFooter();
        loadNextPage();
    }

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
