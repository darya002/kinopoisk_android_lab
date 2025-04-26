package com.example.androidlabkinopoisk.movies

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
    @SerializedName("name") val title: String,
    val year: Int,
    val poster: Poster?
) : Serializable

