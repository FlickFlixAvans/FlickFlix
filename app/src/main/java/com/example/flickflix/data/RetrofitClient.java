package com.example.flickflix.data;

import androidx.annotation.NonNull;

import com.example.flickflix.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    /**
     * Add the API Key as Header to the HTTP Client
     */
    private static OkHttpClient getHttpClient() {
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.networkInterceptors().add(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();
                String bearerToken = "Bearer " + BuildConfig.TMDB_BEARER_TOKEN;
                requestBuilder.header("Authorization", bearerToken);

                return chain.proceed(requestBuilder.build());
            }
        });

        return httpClient;
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
