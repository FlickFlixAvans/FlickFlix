package com.example.flickflix.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.flickflix.R;
import com.example.flickflix.model.Genre;
import com.example.flickflix.model.Movie;
import com.example.flickflix.viewmodel.GenreViewModel;
import com.example.flickflix.viewmodel.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    private final String LOG_TAG = this.getClass().getSimpleName();
    ImageView imgMovieBanner;
    TextView tvMovieDetails;
    TextView tvMovieDescription;
    VideoView videoViewMovieTrailer;
    MovieViewModel movieViewModel;
    GenreViewModel genreViewModel;
    private List<Genre> genres = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_page);

        Log.i(LOG_TAG, "onCreate");

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        genreViewModel = new ViewModelProvider(this).get(GenreViewModel.class);

        genreViewModel.getGenres().observe(this, genreResponse -> {
            genres = genreResponse.getGenres();
        });

        imgMovieBanner = findViewById(R.id.movie_detail_banner);
        tvMovieDetails = findViewById(R.id.movie_detail_detail);
        tvMovieDescription = findViewById(R.id.movie_detail_description);
        videoViewMovieTrailer = findViewById(R.id.movie_detail_trailer);

        Intent intent = getIntent();
        Movie mMovie = (Movie) intent.getSerializableExtra("added_movie");
        assert mMovie != null;

        movieViewModel.getMovie(mMovie.getId()).observe(this, movie -> {
            Log.i(LOG_TAG, movie.getTitle());

            ActionBar toolbar = getSupportActionBar();
            if (null != toolbar) {
                toolbar.setTitle(movie.getTitle());
            }

            Picasso.get().load(movie.getFullBackdropPath()).into(imgMovieBanner);
            tvMovieDetails.setText(getMovieDetails(movie));
        });
    }

    private StringBuilder getMovieDetails(Movie mMovie) {
        Log.i(LOG_TAG, mMovie.getTitle());
        StringBuilder details = new StringBuilder();

        details.append(mMovie.getRuntime());
        details.append(" minuten\n");
        details.append(getGenresForMovie(mMovie));
        Log.i(LOG_TAG, getGenresForMovie(mMovie));

        return details;
    }

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
}