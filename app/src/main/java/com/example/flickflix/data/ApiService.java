package com.example.flickflix.data;

import com.example.flickflix.data.response.VideoResponse;
import com.example.flickflix.model.Movie;
import com.example.flickflix.data.response.GenreResponse;
import com.example.flickflix.data.response.MovieResponse;
import com.example.flickflix.data.response.RequestTokenResponse;
import com.example.flickflix.data.response.SessionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("authentication/token/new")
    Call<RequestTokenResponse> createRequestToken();

    @GET("authentication/session/new")
    Call<SessionResponse> createSession(@Query("request_token") String requestToken);

    @GET("discover/movie")
    Call<MovieResponse> getMovies(@Query("page") Integer page, @Query("sort_by") String sortBy, @Query("include_adult") Boolean includeAdult, @Query("with_genres") String withGenres);

    @GET("genre/movie/list")
    Call<GenreResponse> getGenres();

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") Integer movieId);

    @GET("movie/{movie_id}/videos")
    Call<VideoResponse> getVideos(@Path("movie_id") Integer movieId);
}
