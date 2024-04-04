package com.example.flickflix.data.response;

import com.google.gson.annotations.SerializedName;

public class AccessTokenResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("account_id")
    private String accountId;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
