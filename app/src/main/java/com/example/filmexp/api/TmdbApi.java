package com.example.filmexp.api;

import com.example.filmexp.model.Filme_Resposta;
import com.example.filmexp.model.GenreResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbApi {
    @GET("genre/movie/list")
    Call<GenreResponse> getGenres();

    @GET("discover/movie")
    Call<Filme_Resposta> getMoviesByGenre(@Query("with_genres") int genreId);
}