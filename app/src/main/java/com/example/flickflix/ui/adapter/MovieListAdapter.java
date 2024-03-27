package com.example.flickflix.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flickflix.R;
import com.example.flickflix.data.model.Movie;
import com.example.flickflix.viewmodel.MovieListViewHolder;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListViewHolder> {

    private List<Movie> mMovieList;

    public List<Movie> getmMovieList() {
        return mMovieList;
    }

    private LayoutInflater mInflater;

    public MovieListAdapter(Context context, List<Movie> mMovieList) {
        mInflater = LayoutInflater.from(context);
        this.mMovieList = mMovieList;
    }

    public void setmMovieList(List<Movie> movies) {
        this.mMovieList = movies;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieListViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {
        Movie mMovie = mMovieList.get(position);
        holder.tvMovieTitle.setText(mMovie.getTitle());
        holder.tvMovieGenre.setText(mMovie.getGenre());
        holder.tvMovieReleaseDate.setText(mMovie.getReleaseDate());
        holder.tvMovieRating.setText(mMovie.getRating());

        String url = mMovie.getPosterPath();
        Picasso.get().load(url).into(holder.imgMoviePoster);
    }

    @Override
    public int getItemCount() {
        if (mMovieList != null) {
            return mMovieList.size();
        } else return 0;
    }
}