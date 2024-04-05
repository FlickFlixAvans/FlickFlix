package com.example.flickflix.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Review implements Serializable {
    @SerializedName("author")
    private String author;
    @SerializedName("author_details")
    private ReviewAuthor authorDetails;
    @SerializedName("content")
    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ReviewAuthor getAuthorDetails() {
        return authorDetails;
    }

    public String getContent() {
        return content;
    }

    public void setAuthorDetails(ReviewAuthor authorDetails) {
        this.authorDetails = authorDetails;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public class ReviewAuthor {
        @SerializedName("name")
        private String name;
        @SerializedName("username")
        private String username;
        @SerializedName("avatar_path")
        private String avatarPath;
        @SerializedName("rating")
        private String Rating;

        public String getAvatarPath() {
            return avatarPath;
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

        public void setAvatarPath(String avatarPath) {
            this.avatarPath = avatarPath;
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
}
