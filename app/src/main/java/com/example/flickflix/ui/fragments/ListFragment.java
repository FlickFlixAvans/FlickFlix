package com.example.flickflix.ui.fragments;

// ... [other imports] ...

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
import com.example.flickflix.databinding.FragmentListBinding;
import com.example.flickflix.ui.adapter.ListAdapter;
import com.example.flickflix.ui.adapter.MovieListAdapter;
import com.example.flickflix.ui.adapter.PaginationScrollListener;
import com.example.flickflix.viewmodel.GenreViewModel;
import com.example.flickflix.viewmodel.ListViewModel;
import com.example.flickflix.viewmodel.MovieViewModel;

import java.util.List;

public class ListFragment extends Fragment {
    private static final String TAG = PaginationScrollListener.class.getSimpleName();
    private FragmentHomeBinding binding;

    ListViewModel listViewModel;
    ListAdapter adapter;
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
        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);

        // Observe genres LiveData from ViewModel
        listViewModel.getLists().observe(getViewLifecycleOwner(), listResponse -> {
            adapter.setLists(listResponse.getLists());
            adapter.notifyDataSetChanged();
        });


        // Create adapter
        adapter = new ListAdapter();
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

        // Fetch the lists from the API
        listViewModel.getLists(currentPage).observe(getViewLifecycleOwner(), listResponse -> {
            if (listResponse != null) {
                adapter.removeLoadingFooter();
                isLoading = false;

                // Add the fetched lists to the adapter
                List<List> lists = listResponse.getResults();
                TOTAL_PAGES = listResponse.getTotalPages();

                adapter.addAll(lists);

                // Add loading footer if this is not the last page
                if (currentPage != TOTAL_PAGES) {
                    adapter.addLoadingFooter();
                } else {
                    isLastPage = true;
                }
            } else {
                Toast.makeText(getContext(), "Failed to load lists!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}