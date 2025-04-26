package com.example.androidlabkinopoisk.movies

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("docs") val movies: List<Movie>
)
