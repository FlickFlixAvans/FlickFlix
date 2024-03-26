package com.example.flickflix.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.flickflix.databinding.FragmentListsBinding;
import com.example.flickflix.viewmodel.ListsViewModel;

public class ListsFragment extends Fragment {

    private FragmentListsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ListsViewModel listsViewModel =
                new ViewModelProvider(this).get(ListsViewModel.class);

        binding = FragmentListsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}