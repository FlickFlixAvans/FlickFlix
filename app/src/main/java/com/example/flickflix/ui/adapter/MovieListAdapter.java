package com.example.flickflix.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flickflix.R;
import com.example.flickflix.model.Genre;
import com.example.flickflix.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private final List<Movie> results = new ArrayList<>();
    private List<Genre> genres = new ArrayList<>();
    private Boolean isLoadingAdded = false;
    private OnClickListener onClickListener;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                // Return the Movie ViewHolder
                View mItemView = inflater.inflate(R.layout.movie_list_item, parent, false);
                viewHolder = new MovieVH(mItemView);
                break;
            case LOADING:
                // Return the Loading ViewHolder
                View mitemview = inflater.inflate(R.layout.movie_item_progress, parent, false);
                viewHolder = new LoadingVH(mitemview);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie mMovie = results.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH movieVH = (MovieVH) holder;

                // Show the info in the UI
                movieVH.tvMovieTitle.setText(mMovie.getTitle());
                String movieRating = mMovie.getFormattedVoteAverage() + "/10";
                movieVH.tvMovieRating.setText(movieRating);
                movieVH.tvMovieReleaseDate.setText(mMovie.getFormattedReleaseDate());

                // Get genres & show in UI
                String genres = getGenresForMovie(mMovie);
                movieVH.tvMovieGenre.setText(genres);

                // Load movie poster using Picasso
                String url = mMovie.getFullPosterPath();
                Picasso.get().load(url).into(movieVH.imgMoviePoster);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onClickListener != null) {
                            onClickListener.onClick(position, mMovie);
                        }
                    }
                });

                break;
            case LOADING:
                // Do nothing
                break;
        }
    }

    @Override
    public int getItemCount() {
        return results == null ? 0 : results.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == results.size() - 1 && isLoadingAdded) ? 1 : 0;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /**
     * Get the genres for a movie in a text representative way
     */
    private String getGenresForMovie(Movie movie) {
        StringBuilder genreStringBuilder = new StringBuilder();

        if (movie.getGenreIds() != null && !movie.getGenreIds().isEmpty()) {
            int size = movie.getGenreIds().size();
            for (int i = 0; i < size; i++) {
                int genreId = movie.getGenreIds().get(i);
                for (Genre genre : genres) {
                    if (genre.getId() == genreId) {
                        if (genreStringBuilder.length() > 0) {
                            // Add an "," or "and" between the genres
                            if (i == size - 1 && size > 1) {
                                genreStringBuilder.append(" en ");
                            } else {
                                genreStringBuilder.append(", ");
                            }
                        }

                        genreStringBuilder.append(genre.getName());
                        break;
                    }
                }
            }
        }

        return genreStringBuilder.toString();
    }

    /**
     * Helpers
     */
    public void add(Movie movie) {
        results.add(movie);
        notifyItemInserted(results.size() - 1);
    }

    public void addAll(List<Movie> movies) {
        for (Movie movie : movies) {
            add(movie);
        }
    }

    public void clear() {
        results.clear();
        notifyDataSetChanged();
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Movie());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = results.size() - 1;
        Movie movie = getItem(position);

        if (movie != null) {
            results.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Movie getItem(int position) {
        return results.get(position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, Movie mMovie);
    }

    /**
     * View Holders
     */
    protected static class MovieVH extends RecyclerView.ViewHolder {
        public ImageView imgMoviePoster;
        public TextView tvMovieTitle;
        public TextView tvMovieGenre;
        public TextView tvMovieReleaseDate;
        public TextView tvMovieRating;

        public MovieVH(View itemView) {
            super(itemView);

            imgMoviePoster = itemView.findViewById(R.id.movie_list_image_view);
            tvMovieTitle = itemView.findViewById(R.id.movie_list_movie_title);
            tvMovieGenre = itemView.findViewById(R.id.movie_list_movie_genre);
            tvMovieReleaseDate = itemView.findViewById(R.id.movie_list_release_date);
            tvMovieRating = itemView.findViewById(R.id.movie_list_rating);
        }
    }

    protected static class LoadingVH extends RecyclerView.ViewHolder {
        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}