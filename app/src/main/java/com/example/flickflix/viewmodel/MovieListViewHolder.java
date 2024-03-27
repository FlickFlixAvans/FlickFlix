package com.example.flickflix.viewmodel;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flickflix.R;
import com.example.flickflix.ui.adapter.MovieListAdapter;

public class MovieListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imgMoviePoster;
    public TextView tvMovieTitle;
    public TextView tvMovieGenre;
    public TextView tvMovieReleaseDate;
    public TextView tvMovieRating;

    public MovieListViewHolder(@NonNull View itemView, MovieListAdapter movieListAdapter) {
        super(itemView);
        imgMoviePoster = itemView.findViewById(R.id.movie_list_image_view);
        tvMovieTitle = itemView.findViewById(R.id.movie_list_movie_title);
        tvMovieGenre = itemView.findViewById(R.id.movie_list_movie_genre);
        tvMovieReleaseDate = itemView.findViewById(R.id.movie_list_release_date);
        tvMovieRating = itemView.findViewById(R.id.movie_list_rating);
        itemView.setOnClickListener(this);
    }

    public void onClick(View view) {
        /*int position = getAdapterPosition();

        Movie mMovie = mMovieList.get(position);

        Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);

        intent.putExtra("added_movie", mMovie);

        view.getContext().startActivity(intent);*/
    }
}
