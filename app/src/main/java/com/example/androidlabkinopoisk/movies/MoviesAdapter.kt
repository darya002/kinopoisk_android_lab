package com.example.androidlabkinopoisk.movies

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidlabkinopoisk.activities.DetailActivity
import com.example.androidlabkinopoisk.R

class MoviesAdapter(private var movies: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagePoster: ImageView = itemView.findViewById(R.id.imagePoster)
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textYear: TextView = itemView.findViewById(R.id.textYear)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]


        holder.textTitle.text = movie.title
        holder.textYear.text = movie.year.toString()
        Glide.with(holder.itemView.context)
            .load(movie.poster?.url ?: R.drawable.default_poster)
            .into(holder.imagePoster)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java).apply {
                putExtra("movie", movie)
            }
            holder.itemView.context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}
