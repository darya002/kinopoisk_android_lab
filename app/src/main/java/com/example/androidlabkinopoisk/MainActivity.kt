package com.example.androidlabkinopoisk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidlabkinopoisk.models.MovieResponse
import com.example.androidlabkinopoisk.models.MoviesAdapter
import com.example.androidlabkinopoisk.ui.theme.AndroidLabKinopoiskTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlabkinopoisk.models.LoginActivity
import com.example.androidlabkinopoisk.models.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val isLoggedIn = prefs.getBoolean("isLoggedIn", false)

        if (!isLoggedIn) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.buttonWatched).setOnClickListener {
            showSavedList("watched")
        }

        findViewById<Button>(R.id.buttonPlanned).setOnClickListener {
            showSavedList("planned")
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        moviesAdapter = MoviesAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = moviesAdapter
        val buttonLogout: Button = findViewById(R.id.buttonLogout)
        buttonLogout.setOnClickListener {
            val prefs = getSharedPreferences("auth", MODE_PRIVATE)
            prefs.edit().clear().apply()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        findViewById<Button>(R.id.buttonShowSaved).setOnClickListener {
            val prefs = getSharedPreferences("movies", MODE_PRIVATE)
            val gson = Gson()
            val type = object : TypeToken<List<Movie>>() {}.type
            val watched = gson.fromJson<List<Movie>>(prefs.getString("watched", "[]"), type)
            val planned = gson.fromJson<List<Movie>>(prefs.getString("planned", "[]"), type)

            val all = watched + planned
            moviesAdapter.updateMovies(all)
        }

        kinopoiskApi.getMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("API Response", response.body().toString())
                    response.body()?.let { movieResponse ->
                        moviesAdapter.updateMovies(movieResponse.movies)
                    }
                } else {
                    Log.e("MainActivity", "Ошибка: ${response.code()}")
                    Toast.makeText(this@MainActivity, "Ошибка загрузки фильмов", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("MainActivity", "Ошибка: ${t.message}")
                Toast.makeText(this@MainActivity, "Сетевая ошибка", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun showSavedList(key: String) {
        val prefs = getSharedPreferences("movies", MODE_PRIVATE)
        val gson = Gson()
        val type = object : TypeToken<List<Movie>>() {}.type
        val saved = gson.fromJson<List<Movie>>(prefs.getString(key, "[]"), type)
        moviesAdapter.updateMovies(saved)
    }
}


