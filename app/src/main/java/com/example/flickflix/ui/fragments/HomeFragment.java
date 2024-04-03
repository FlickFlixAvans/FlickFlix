package com.example.flickflix.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
    Button btnOpenFilter;
    RadioGroup radioGroupSort;
    DrawerLayout drawerLayout;
    Button btnApplyFilter;
    CheckBox checkbox18;

    private static final int PAGE_START = 1;
    private int TOTAL_PAGES;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;

    private String sortBy = "popularity.desc";
    private Boolean includeAdult = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Init view models
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        genreViewModel = new ViewModelProvider(this).get(GenreViewModel.class);

        // Observe genres LiveData from ViewModel
        genreViewModel.getGenres().observe(getViewLifecycleOwner(), genreResponse -> {
            if (genreResponse != null) {
                adapter.setGenres(genreResponse.getGenres());
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "Failed to load genres!", Toast.LENGTH_SHORT).show();
            }
        });

        // Create adapter
        adapter = new MovieListAdapter();
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Load the filters
        initFilters(view);

        // Load the apply filters button
        initApplyFiltersBtn(view);

        // Define the recycler view
        defineRecyclerView(view);

        // Show loading bar & load the first page
        loadFirstPage();
    }

    private void defineRecyclerView(View view) {
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
    }

    private void initFilters(View view) {
        // Filter drawer layout
        drawerLayout = view.findViewById(R.id.drawer_layout);

        btnOpenFilter = view.findViewById(R.id.btn_open_filter);
        btnOpenFilter.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Sort by
        radioGroupSort = view.findViewById(R.id.rg_sort);
        radioGroupSort.setOnCheckedChangeListener((group, checkedId) -> {
            // Get the tag (sort by) from the selected radio button
            RadioButton radioButton = view.findViewById(checkedId);
            sortBy = radioButton.getTag().toString();
        });

        // Filters
        checkbox18 = view.findViewById(R.id.cb_18);
        checkbox18.setOnCheckedChangeListener((buttonView, isChecked) -> includeAdult = isChecked);


        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", 0);
        boolean kidsFriendly = sharedPreferences.getBoolean("switch_kids_friendly", false);

        // Hides 18+ filter and adult movies
        if (kidsFriendly) {
            checkbox18.setVisibility(View.GONE);
            includeAdult = false;
        }

        // Selected genres
    }

    private void initApplyFiltersBtn(View view) {
        // Apply filters
        btnApplyFilter = view.findViewById(R.id.apply_filter_button);
        btnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reload the page
                // This will make sure that the selected filters will be applied
                reloadPage();

                // Close filter navigation drawer
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    private void loadNextPage() {
        Log.i(TAG, "Loading movies page " + currentPage);

        // Fetch the now playing movies from the API
        movieViewModel.getMovies(currentPage, sortBy, includeAdult).observe(getViewLifecycleOwner(), movieResponse -> {
            adapter.removeLoadingFooter();
            isLoading = false;

            if (movieResponse != null) {
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

    private void loadFirstPage() {
        adapter.addLoadingFooter();
        loadNextPage();
    }

    private void reloadPage() {
        adapter.clear();

        // Reset page to 1
        currentPage = PAGE_START;
        isLoading = true;
        isLastPage = false;

        // Load first page
        loadFirstPage();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}