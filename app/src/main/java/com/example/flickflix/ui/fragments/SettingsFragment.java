package com.example.flickflix.ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.flickflix.R;
import com.example.flickflix.data.SharedPreferencesManager;
import com.example.flickflix.ui.AuthenticateTMDBActivity;

public class SettingsFragment extends Fragment {

    Button btnLogout;
    SwitchCompat switchKidsFriendly;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize views
        btnLogout = root.findViewById(R.id.btn_logout);
        switchKidsFriendly = root.findViewById(R.id.switch_kids_friendly);

        // Load switch state from shared preferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", 0);
        switchKidsFriendly.setChecked(sharedPreferences.getBoolean("switch_kids_friendly", false));

        // Set listener for the switch
        switchKidsFriendly.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save switch state to shared preferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("switch_kids_friendly", isChecked);
            editor.apply();

            // Check if the switch is toggled on
            if (isChecked) {
                // Switch is toggled on
                Toast.makeText(getContext(), "Kids-friendly mode enabled", Toast.LENGTH_SHORT).show();

            } else {
                // Switch is toggled off
                Toast.makeText(getContext(), "Kids-friendly mode disabled", Toast.LENGTH_SHORT).show();

            }
        });

        // Set click listener for logout button
        btnLogout.setOnClickListener(event -> {
            // Delete the session
            SharedPreferencesManager manager = new SharedPreferencesManager(getContext());
            manager.deleteSession();

            // Redirect to authenticate activity
            Toast.makeText(getContext(), "Successfully logged out!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), AuthenticateTMDBActivity.class));
            requireActivity().finish();
        });

        return root;
    }
}
