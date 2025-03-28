package com.example.androidlabkinopoisk.models

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("name")
    val title: String,
    val year: Int,
    val poster: Poster?
)

