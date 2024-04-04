package com.example.flickflix.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.flickflix.R;
import com.example.flickflix.data.SharedPreferencesManager;
import com.example.flickflix.databinding.FragmentSettingsBinding;
import com.example.flickflix.ui.AuthenticateTMDBActivity;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    Button btnLogout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Add logout button
        btnLogout = view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(event -> {
            // Delete the session
            SharedPreferencesManager manager = new SharedPreferencesManager(getContext());
            manager.deleteCredentials();

            // Redirect to authenticate activity
            Toast.makeText(getContext(), "Successfully logged out!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getContext(), AuthenticateTMDBActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}