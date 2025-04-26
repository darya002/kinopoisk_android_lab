package com.example.androidlabkinopoisk

import com.example.androidlabkinopoisk.movies.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface KinopoiskApi {
    @GET("v1/movie")
    fun getMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Call<MovieResponse>
}