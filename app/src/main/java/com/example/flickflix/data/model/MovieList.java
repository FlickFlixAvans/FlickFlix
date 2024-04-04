package com.example.flickflix.data.model;

import com.google.gson.annotations.SerializedName;

public class MovieList {
    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("account_object_id")
    private String accountObjectId;

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccountObjectId() {
        return accountObjectId;
    }

    public void setAccountObjectId(String accountObjectId) {
        this.accountObjectId = accountObjectId;
    }
}