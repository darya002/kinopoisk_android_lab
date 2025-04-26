package com.example.androidlabkinopoisk.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.androidlabkinopoisk.R
import com.example.androidlabkinopoisk.movies.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailActivity : AppCompatActivity() {

    private lateinit var movie: Movie
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        prefs = getSharedPreferences("movies", MODE_PRIVATE)

        movie = intent.getSerializableExtra("movie") as? Movie
            ?: run {
                finish()
                return
            }

        val imagePoster: ImageView = findViewById(R.id.imageDetailPoster)
        val textTitle: TextView = findViewById(R.id.textDetailTitle)
        val textYear: TextView = findViewById(R.id.textDetailYear)

        textTitle.text = movie.title
        textYear.text = movie.year.toString()

        Glide.with(this)
            .load(movie.poster?.url ?: R.drawable.default_poster)
            .into(imagePoster)

        findViewById<Button>(R.id.buttonAddWatched).setOnClickListener {
            saveMovie("watched", movie)
        }

        findViewById<Button>(R.id.buttonAddPlanned).setOnClickListener {
            saveMovie("planned", movie)
        }
    }

    private fun saveMovie(key: String, movie: Movie) {
        val gson = Gson()
        val currentJson = prefs.getString(key, "[]")
        val type = object : TypeToken<MutableList<Movie>>() {}.type
        val list: MutableList<Movie> = gson.fromJson(currentJson, type)

        if (list.any { it.title == movie.title }) {
            Toast.makeText(this, "Уже в списке", Toast.LENGTH_SHORT).show()
        } else {
            list.add(movie)
            prefs.edit().putString(key, gson.toJson(list)).apply()
            Toast.makeText(this, "Добавлено в $key", Toast.LENGTH_SHORT).show()
        }
    }
}