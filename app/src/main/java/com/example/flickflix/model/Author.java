package com.example.flickflix.model;

import java.util.jar.Attributes;

public class Author {
    private String name;
    private String username;
    private String avatar_path;
    private String Rating;

    public String getAvatar_path() {
        return avatar_path;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return Rating;
    }

    public String getUsername() {
        return username;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

