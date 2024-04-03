package com.example.flickflix.data;

import com.example.flickflix.data.response.GenreResponse;
import com.example.flickflix.data.response.ListResponse;
import com.example.flickflix.data.response.MovieResponse;
import com.example.flickflix.data.response.RequestTokenResponse;
import com.example.flickflix.data.response.SessionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("authentication/token/new")
    Call<RequestTokenResponse> createRequestToken();

    @GET("authentication/session/new")
    Call<SessionResponse> createSession(@Query("request_token") String requestToken);

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@Query("page") Integer page);

    @GET("genre/movie/list")
    Call<GenreResponse> getGenres();

    @GET("account/{account_object_id}/lists")
    Call<ListResponse> getMovieLists(@Query("page") int page);
}
