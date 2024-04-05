package com.example.flickflix.data;

import com.example.flickflix.data.response.ReviewResponse;
import com.example.flickflix.data.response.VideoResponse;
import com.example.flickflix.model.Movie;
import com.example.flickflix.data.response.GenreResponse;
import com.example.flickflix.data.response.ListResponse;
import com.example.flickflix.data.response.MovieResponse;
import com.example.flickflix.data.response.RequestTokenResponse;
import com.example.flickflix.data.response.AccessTokenResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("4/auth/request_token")
    Call<RequestTokenResponse> createRequestToken(@Query("redirect_to") String redirectTo);

    @POST("4/auth/access_token")
    Call<AccessTokenResponse> createAccessToken(@Query("request_token") String requestToken);

    @GET("3/discover/movie")
    Call<MovieResponse> getMovies(@Query("page") Integer page, @Query("sort_by") String sortBy, @Query("include_adult") Boolean includeAdult, @Query("with_genres") String withGenres);

    @GET("3/genre/movie/list")
    Call<GenreResponse> getGenres();

    @GET("4/account/{account_object_id}/lists")
    Call<ListResponse> getMovieLists(
            @Path("account_object_id") String accountObjectId,
            @Query("page") Integer page,
            @Header("Authorization") String authorization
    );

    @POST("4/list?public=true&iso_3166_1=US&iso_639_1=en")
    Call<ResponseBody> createMovieList(@Query("name") String name, @Query("description") String description, @Header("Authorization") String authorization);

    @GET("3/movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") Integer movieId);

    @GET("3/movie/{movie_id}/videos")
    Call<VideoResponse> getVideos(@Path("movie_id") Integer movieId);

    @GET("3/movie/{movie_id}/reviews?page=1")
    Call<ReviewResponse> getReviews(@Path("movie_id") Integer movieId);
}
