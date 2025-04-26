package com.example.androidlabkinopoisk.movies

import java.io.Serializable

data class Poster(
    val url: String?,
    val previewUrl: String?
) : Serializable