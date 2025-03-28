package com.example.androidlabkinopoisk.models

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("name") val title: String,
    @SerializedName("year") val year: Int,
    @SerializedName("posterUrl") val posterUrl: String?
)

