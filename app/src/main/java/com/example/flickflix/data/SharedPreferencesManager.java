package com.example.flickflix.data;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String NAME = "SessionSharedPreferences";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String ACCOUNT_ID = "ACCOUNT_ID";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public SharedPreferencesManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveAccessToken(String accessToken) {
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.commit();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(ACCESS_TOKEN, null);
    }

    public void saveAccountId(String accountId) {
        editor.putString(ACCOUNT_ID, accountId);
        editor.commit();
    }

    public String getAccountId() {
        return sharedPreferences.getString(ACCOUNT_ID, null);
    }

    public void deleteCredentials() {
        editor.remove(ACCESS_TOKEN);
        editor.remove(ACCOUNT_ID);
        editor.commit();
    }
}
