package com.example.flickflix.ui.fragments;

import android.os.Bundle;
import android.util.Log;
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

import com.example.flickflix.R;
import com.example.flickflix.data.model.Movie;
import com.example.flickflix.data.model.MovieList;
import com.example.flickflix.databinding.FragmentHomeBinding;
import com.example.flickflix.databinding.FragmentListBinding;
import com.example.flickflix.ui.adapter.ListAdapter;
import com.example.flickflix.ui.adapter.MovieListAdapter;
import com.example.flickflix.ui.adapter.PaginationScrollListener;
import com.example.flickflix.viewmodel.GenreViewModel;
import com.example.flickflix.viewmodel.ListViewModel;
import com.example.flickflix.viewmodel.MovieViewModel;

import java.util.List;

public class ListFragment extends Fragment {
    private FragmentListBinding binding;
    ListViewModel listViewModel;
    ListAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
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
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                loadNextPage();
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
        loadNextPage();
    }

    private void loadNextPage() {
        listViewModel.getLists(currentPage).observe(getViewLifecycleOwner(), listResponse -> {
            isLoading = false;
            if (listResponse != null) {
                TOTAL_PAGES = listResponse.getTotalPages(); // Ensure this method exists
                List<List> movieLists = listResponse.getResults(); // Assuming getResults returns List<MovieList>
                if (currentPage <= TOTAL_PAGES) {
                    adapter.addAll(movieLists); // Make sure addAll accepts List<MovieList>
                    if (currentPage != TOTAL_PAGES) {
                        adapter.addLoadingFooter();
                    } else {
                        isLastPage = true;
                    }
                }
            } else {
                Toast.makeText(getContext(), "Failed to load data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
