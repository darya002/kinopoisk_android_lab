package com.example.androidlabkinopoisk

import android.os.Bundle
import android.util.Log
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


class MainActivity : AppCompatActivity() {

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Настройка RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        moviesAdapter = MoviesAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = moviesAdapter

        // Выполнение запроса к API
        kinopoiskApi.getMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { movieResponse ->
                        moviesAdapter.updateMovies(movieResponse.movies)
                    }
                } else {
                    Log.e("MainActivity", "Ошибка: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("MainActivity", "Ошибка: ${t.message}")
            }
        })
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidLabKinopoiskTheme {
        Greeting("Android")
    }
}