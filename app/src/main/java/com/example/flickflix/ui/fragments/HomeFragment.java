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
import com.example.flickflix.databinding.FragmentHomeBinding;
import com.example.flickflix.ui.adapter.MovieListAdapter;
import com.example.flickflix.ui.adapter.PaginationScrollListener;
import com.example.flickflix.viewmodel.GenreViewModel;
import com.example.flickflix.viewmodel.MovieViewModel;

import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = PaginationScrollListener.class.getSimpleName();
    private FragmentHomeBinding binding;

    MovieViewModel movieViewModel;
    GenreViewModel genreViewModel;
    MovieListAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;

    private static final int PAGE_START = 1;
    private int TOTAL_PAGES;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Init view models
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        genreViewModel = new ViewModelProvider(this).get(GenreViewModel.class);

        // Observe genres LiveData from ViewModel
        genreViewModel.getGenres().observe(getViewLifecycleOwner(), genreResponse -> {
            adapter.setGenres(genreResponse.getGenres());
            adapter.notifyDataSetChanged();
        });

        // Create adapter
        adapter = new MovieListAdapter();
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        currentPage = PAGE_START;

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Define the Recycler View
        recyclerView = view.findViewById(R.id.rv_movies_list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        // Add Pagination Scroll listener
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

        // Show loading bar & load the first page
        adapter.addLoadingFooter();
        loadNextPage();
    }

    private void loadNextPage() {
        Log.i(TAG, "Loading movies page " + currentPage);

        // Fetch the now playing movies from the API
        movieViewModel.getNowPlayingMovies(currentPage).observe(getViewLifecycleOwner(), movieResponse -> {
            if (movieResponse != null) {
                adapter.removeLoadingFooter();
                isLoading = false;

                // Add the fetched movies to the adapter
                List<Movie> movies = movieResponse.getResults();
                TOTAL_PAGES = movieResponse.getTotalPages();

                adapter.addAll(movies);

                // Add loading footer if this is not the last page
                if (currentPage != TOTAL_PAGES) {
                    adapter.addLoadingFooter();
                } else {
                    isLastPage = true;
                }
            } else {
                Toast.makeText(getContext(), "Failed to load movies!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}