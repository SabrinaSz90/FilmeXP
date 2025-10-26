package com.example.filmexp.api;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "35f1daaa1452ca768ad97aa17bae1f3b";

    private static OkHttpClient getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl url = original.url().newBuilder()
                            .addQueryParameter("api_key", API_KEY)
                            .build();
                    Request request = original.newBuilder().url(url).build();
                    return chain.proceed(request);
                })
                .build();
    }

    public static TmdbApi getApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(TmdbApi.class);
    }
}