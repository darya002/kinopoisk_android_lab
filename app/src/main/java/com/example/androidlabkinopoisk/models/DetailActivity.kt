package com.example.androidlabkinopoisk.models

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.androidlabkinopoisk.R

class DetailActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val title = intent.getStringExtra("title")
        val year = intent.getIntExtra("year", 0)
        val posterUrl = intent.getStringExtra("poster")

        val titleText: TextView = findViewById(R.id.textDetailTitle)
        val yearText: TextView = findViewById(R.id.textDetailYear)
        val posterImage: ImageView = findViewById(R.id.imageDetailPoster)

        titleText.text = title
        yearText.text = "Год: $year"

        Glide.with(this)
            .load(posterUrl ?: R.drawable.default_poster)
            .into(posterImage)
    }
}
