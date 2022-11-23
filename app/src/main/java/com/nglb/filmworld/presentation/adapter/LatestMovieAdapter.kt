package com.nglb.filmworld.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nglb.filmworld.R
import com.nglb.filmworld.data.model.LatestMovie
import com.nglb.filmworld.utils.Constant
import javax.inject.Inject

class LatestMovieAdapter @Inject constructor(
) : RecyclerView.Adapter<LatestMovieAdapter.LatestMovieViewHolder>() {

    private var LatestMovies: ArrayList<LatestMovie> = ArrayList()

    class LatestMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _iv_poster_latest = itemView.findViewById<ImageView>(R.id.iv_poster_latest)
        var _vote_avg_latest = itemView.findViewById<TextView>(R.id.vote_avg_latest)
        var _vote_count_latest = itemView.findViewById<TextView>(R.id.vote_count_latest)
        var _tv_latest_title = itemView.findViewById<TextView>(R.id.tv_latest_title)
        fun bind(LatestMovie: LatestMovie) {
            _vote_avg_latest.text = LatestMovie.vote_average.toString()
            _vote_count_latest.text = LatestMovie.vote_count.toString() + " Votes"
            _tv_latest_title.text = LatestMovie.title
            Glide.with(itemView.context)
                .load(Constant.BASE_URL_IMG_POSTER + LatestMovie.poster_path)
                .into(_iv_poster_latest)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LatestMovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.latest_movie_list, parent,
                false
            )
        )

    override fun getItemCount(): Int = LatestMovies.size

    override fun onBindViewHolder(holder: LatestMovieViewHolder, position: Int) =
        holder.bind(LatestMovies[position])

    fun addData(LatestMovies: List<LatestMovie>) {
        this.LatestMovies.apply {
            clear()
            addAll(LatestMovies)
        }
    }

}