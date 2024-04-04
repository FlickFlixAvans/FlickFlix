package com.example.flickflix.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Review implements Serializable {
    @SerializedName("author")
    private String author;
    @SerializedName("author_details")
    private Author authorDetails;
    @SerializedName("Content")
    private String content;

    public Author getAuthorDetails() {
        return authorDetails;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAuthorDetails(Author authorDetails) {
        this.authorDetails = authorDetails;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
