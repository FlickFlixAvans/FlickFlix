package com.example.flickflix.data;

import com.example.flickflix.data.response.GenreResponse;
import com.example.flickflix.data.response.ListResponse;
import com.example.flickflix.data.response.MovieResponse;
import com.example.flickflix.data.response.RequestTokenResponse;
import com.example.flickflix.data.response.AccessTokenResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("4/auth/request_token")
    Call<RequestTokenResponse> createRequestToken(@Query("redirect_to") String redirectTo);

    @POST("4/auth/access_token")
    Call<AccessTokenResponse> createAccessToken(@Query("request_token") String requestToken);

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
