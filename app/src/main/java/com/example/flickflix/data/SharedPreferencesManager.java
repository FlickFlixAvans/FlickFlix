package com.example.flickflix.data;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String NAME = "SessionSharedPreferences";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public SharedPreferencesManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(String sessionId) {
        editor.putString("SESSION_ID", sessionId);
        editor.commit();
    }

    public String getSessionId() {
        return sharedPreferences.getString("SESSION_ID", null);
    }

    public void deleteSession() {
        editor.remove("SESSION_ID");
        editor.commit();
    }

    // New method to save account ID
    public void saveAccountId(String accountId) {
        editor.putString("ACCOUNT_ID", accountId);
        editor.commit();
    }

    // New method to get account ID
    public String getAccountId() {
        return sharedPreferences.getString("ACCOUNT_ID", null);
    }

    // New method to delete account ID
    public void deleteAccountId() {
        editor.remove("ACCOUNT_ID");
        editor.commit();
    }
}
