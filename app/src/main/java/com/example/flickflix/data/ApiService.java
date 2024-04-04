package com.example.flickflix.data;

import com.example.flickflix.data.response.GenreResponse;
import com.example.flickflix.data.response.ListResponse;
import com.example.flickflix.data.response.MovieResponse;
import com.example.flickflix.data.response.RequestTokenResponse;
import com.example.flickflix.data.response.SessionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("3/authentication/token/new")
    Call<RequestTokenResponse> createRequestToken();

    @GET("3/authentication/session/new")
    Call<SessionResponse> createSession(@Query("request_token") String requestToken);

    @GET("3/movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@Query("page") Integer page);

    @GET("3/genre/movie/list")
    Call<GenreResponse> getGenres();

    @GET("4/account/{account_object_id}/lists")
    Call<ListResponse> getMovieLists(
            @Path("account_object_id") String accountObjectId,
            @Query("page") Integer page
    );
}
