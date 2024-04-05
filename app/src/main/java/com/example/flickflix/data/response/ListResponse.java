package com.example.flickflix.data.response;

import com.example.flickflix.model.Genre;
import com.example.flickflix.model.MovieList;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListResponse {
    @SerializedName("lists")
    private List<Genre> lists;

    @SerializedName("results")
    private List<MovieList> results;

    @SerializedName("total_pages")
    private int totalPages;

    public List<Genre> getLists() {
        return lists;
    }

    public void setLists(List<Genre> lists) {
        this.lists = lists;
    }

    public List<MovieList> getResults() {
        return results;
    }

    public void setResults(List<MovieList> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
