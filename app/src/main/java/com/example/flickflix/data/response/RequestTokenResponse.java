package com.example.flickflix.data.response;

import com.google.gson.annotations.SerializedName;

public class RequestTokenResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("request_token")
    private String requestToken;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }
}
