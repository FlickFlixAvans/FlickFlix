package com.example.flickflix.data.repository;

import com.google.gson.annotations.SerializedName;

public class RequestSessionResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("session_id")
    private String sessionId;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
