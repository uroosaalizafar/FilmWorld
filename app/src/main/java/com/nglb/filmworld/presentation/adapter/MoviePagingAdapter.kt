package com.nglb.filmworld.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nglb.filmworld.R
import com.nglb.filmworld.data.model.Movie
import com.nglb.filmworld.utils.Constant


class MoviePagingAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Movie, MoviePagingAdapter.SearchViewHolder>(COMPARATOR) {

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv_poster = itemView.findViewById<ImageView>(R.id.iv_poster)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            Glide.with(holder.itemView.context).load(Constant.BASE_URL_IMG_POSTER + item.poster_path)
                .into(holder.iv_poster);
            holder.itemView.setOnClickListener {
                listener?.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return SearchViewHolder(view)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }
}

