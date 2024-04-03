package com.example.flickflix.data.response;

import com.example.flickflix.data.model.Genre;
import com.example.flickflix.data.model.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListResponse {
    @SerializedName("lists")
    private List<Genre> lists;

    @SerializedName("results")
    private List<List> results;

    @SerializedName("total_pages")
    private int totalPages;

    public List<Genre> getLists() {
        return lists;
    }

    public void setLists(List<Genre> lists) {
        this.lists = lists;
    }

    public List<List> getResults() {
        return results;
    }

    public void setResults(List<List> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
