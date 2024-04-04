package com.example.flickflix.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flickflix.R;
import com.example.flickflix.model.Genre;
import com.example.flickflix.model.Movie;
import com.example.flickflix.model.Review;
import com.example.flickflix.model.Video;
import com.example.flickflix.ui.adapter.ReviewListAdapter;
import com.example.flickflix.viewmodel.GenreViewModel;
import com.example.flickflix.viewmodel.MovieViewModel;
import com.example.flickflix.viewmodel.ReviewViewModel;
import com.example.flickflix.viewmodel.VideoViewModel;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    private final String LOG_TAG = this.getClass().getSimpleName();
    ImageView imgMovieBanner;
    TextView tvMovieDetails;
    TextView tvMovieDescription;
    MovieViewModel movieViewModel;
    GenreViewModel genreViewModel;
    VideoViewModel videoViewModel;
    private RecyclerView mRecyclerView;
    private ReviewViewModel reviewViewModel;
    TextView tvMovieTrailerTitle;
    private List<Genre> genres = new ArrayList<>();
    private List<Video> videos = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    private Movie mShareMovie;
    private ReviewListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_page);

        Log.i(LOG_TAG, "onCreate");

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        genreViewModel = new ViewModelProvider(this).get(GenreViewModel.class);
        videoViewModel = new ViewModelProvider(this).get(VideoViewModel.class);
        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);

        mRecyclerView = findViewById(R.id.rv_movie_detail_review_list);
        mAdapter = new ReviewListAdapter(this, reviews);
        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        genreViewModel.getGenres().observe(this, genreResponse -> {
            genres = genreResponse.getGenres();
        });

        imgMovieBanner = findViewById(R.id.movie_detail_banner);
        tvMovieDetails = findViewById(R.id.movie_detail_detail);
        tvMovieDescription = findViewById(R.id.movie_detail_description);

        Intent intent = getIntent();
        Movie mMovie = (Movie) intent.getSerializableExtra("added_movie");
        assert mMovie != null;

        movieViewModel.getMovie(mMovie.getId()).observe(this, movie -> {
            mShareMovie = movie;

            ActionBar toolbar = getSupportActionBar();
            if (null != toolbar) {
                toolbar.setTitle(movie.getTitle());
            }

            Picasso.get().load(movie.getFullBackdropPath()).into(imgMovieBanner);
            tvMovieDetails.setText(getMovieDetails(movie));
            tvMovieDescription.setText(movie.getOverview());
        });

        // Load YouTube video
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                videoViewModel.getVideos(mMovie.getId()).observe(MovieDetailActivity.this, videoResponse -> {
                    videos = videoResponse.getVideos(mMovie.getId());
                    if (!videos.isEmpty()) {
                        String videoId = null;
                        for (Video video : videos) {
                            if (video.getOfficial() == true && video.getType().equals("Trailer")) {
                                videoId = video.getKey();
                                break;
                            }
                        }

                        // Load video
                        if(videoId == null) {
                            youTubePlayerView.setVisibility(View.GONE);
                        } else {
                            youTubePlayer.loadVideo(videoId, 0);
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_extra_button) {
            // Handle the click event for the share button
            shareMovieDetails();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getMovieDetails(Movie mMovie) {
        StringBuilder details = new StringBuilder();

        details.append(mMovie.getRuntime());
        details.append(getString(R.string.minuten));
        details.append(getGenresForMovie(mMovie));
        details.append("\nRelease: ");
        details.append(mMovie.getFormattedReleaseDate());
        details.append("\nRating: ");
        details.append(mMovie.getFormattedVoteAverage());
        details.append("/10");

        return details.toString();
    }

    private String getGenresForMovie(Movie movie) {
        StringBuilder genreStringBuilder = new StringBuilder();

        if (movie.getGenres() != null && !movie.getGenres().isEmpty()) {
            int size = movie.getGenres().size();
            for (int i = 0; i < size; i++) {
                if (genreStringBuilder.length() > 0) {
                    // Add an "," or "and" between the genres
                    if (i == size - 1 && size > 1) {
                        genreStringBuilder.append(" en ");
                    } else {
                        genreStringBuilder.append(", ");
                    }
                }
                genreStringBuilder.append(movie.getGenres().get(i).getName());
            }
        }
        return genreStringBuilder.toString();
    }

    private void shareMovieDetails() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getMovieDetails(mShareMovie));
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");

        try {
            startActivity(sendIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onReviewAvailable(ArrayList<Review> reviews) {
        mAdapter.setmReviewList(reviews);
    }

}