package com.example.androidlabkinopoisk.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlabkinopoisk.KinopoiskApi
import com.example.androidlabkinopoisk.R
import com.example.androidlabkinopoisk.movies.Movie
import com.example.androidlabkinopoisk.movies.MovieResponse
import com.example.androidlabkinopoisk.movies.MoviesAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var kinopoiskApi: KinopoiskApi

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val isLoggedIn = prefs.getBoolean("isLoggedIn", false)

        if (!isLoggedIn) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        moviesAdapter = MoviesAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = moviesAdapter

        findViewById<Button>(R.id.buttonLogout).setOnClickListener {
            prefs.edit().clear().apply()
            startActivity(Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
        }

        findViewById<Button>(R.id.buttonShowSaved).setOnClickListener {
            val all = getSavedMovies("watched") + getSavedMovies("planned")
            moviesAdapter.updateMovies(all)
        }

        findViewById<Button>(R.id.buttonWatched).setOnClickListener {
            moviesAdapter.updateMovies(getSavedMovies("watched"))
        }

        findViewById<Button>(R.id.buttonPlanned).setOnClickListener {
            moviesAdapter.updateMovies(getSavedMovies("planned"))
        }

        findViewById<Button>(R.id.buttonShowAll).setOnClickListener {
            fetchMovies()
        }

        fetchMovies()
    }

    private fun fetchMovies() {
        kinopoiskApi.getMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        moviesAdapter.updateMovies(it.movies)
                    }
                } else {
                    Log.e("MainActivity", "Ошибка: ${response.code()}")
                    Toast.makeText(this@MainActivity, "Ошибка загрузки фильмов", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("MainActivity", "Ошибка: ${t.message}")
                Toast.makeText(this@MainActivity, "Сетевая ошибка", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getSavedMovies(key: String): List<Movie> {
        val prefs = getSharedPreferences("movies", MODE_PRIVATE)
        val gson = Gson()
        val type = object : TypeToken<List<Movie>>() {}.type
        return gson.fromJson(prefs.getString(key, "[]"), type)
    }
}