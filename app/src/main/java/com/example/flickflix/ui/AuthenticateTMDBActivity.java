package com.example.flickflix.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.flickflix.R;
import com.example.flickflix.data.SharedPreferencesManager;
import com.example.flickflix.viewmodel.AuthenticationViewModel;

import java.net.URI;
import java.util.HashMap;

public class AuthenticateTMDBActivity extends AppCompatActivity {
    private static final String TAG = AuthenticateTMDBActivity.class.getSimpleName();
    Button signInButton;
    AuthenticationViewModel viewModel;

    private String requestToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authenticate_tmdb);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Init
        viewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);

        // Sign in button click listener
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(v -> launchLoginTab());
    }

    private void launchLoginTab() {
        // Redirect URI
        URI redirectUri = URI.create("oauth://tmdb");

        // Fetch a new request token from the API
        viewModel.getRequestToken(redirectUri.toString()).observe(this, requestToken -> {
            this.requestToken = requestToken;

            if (requestToken != null) {
                // Create the AUTH URL for authentication
                URI authURL = URI.create("https://www.themoviedb.org/auth/access?request_token=" + requestToken);

                // Open URI in browser
                new CustomTabsIntent.Builder().build().launchUrl(this, Uri.parse(authURL.toString()));
            } else {
                Toast.makeText(this, "Failed to get request token", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            Uri callback = intent.getData();

            if (callback != null && callback.getScheme().equals("oauth") && callback.getHost().equals("tmdb")) {
                if (requestToken != null) {
                    // Create access token & store in shared preferences
                    viewModel.createAccessToken(requestToken).observe(this, accessTokenResponse -> {
                        if (accessTokenResponse != null && accessTokenResponse.isSuccess()) {
                            // Store the access_token & account_id in shared preferences
                            SharedPreferencesManager manager = new SharedPreferencesManager(this);

                            manager.saveAccessToken(accessTokenResponse.getAccessToken());
                            manager.saveAccountId(accessTokenResponse.getAccountId());

                            Log.i(TAG, "Saved access token & account id in the shared preferences");

                            Toast.makeText(this, "Successfully logged in!", Toast.LENGTH_SHORT).show();

                            // Redirect to main activity
                            Intent mainIntent = new Intent(this, MainActivity.class);
                            startActivity(mainIntent);
                        } else {
                            Toast.makeText(this, "Failed to create session", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }
}
