package com.nglb.filmworld.utils

import android.content.Intent
import com.nglb.filmworld.data.model.Movie

// i create this fun because it use in two places one in search movie activity and another is in home activity
fun items(movie: Movie , intent: Intent) {
    intent.putExtra(Constant.MOVIE_BACKDROP, movie.backdrop_path)
    intent.putExtra(Constant.MOVIE_POSTER, movie.poster_path)
    intent.putExtra(Constant.MOVIE_TITLE, movie.title)
    intent.putExtra(Constant.MOVIE_RELEASE_DATE, movie.release_date)
    intent.putExtra(Constant.MOVIE_OVERVIEW, movie.overview)
    intent.putExtra(Constant.MOVIE_VOTE_COUNT, movie.vote_count)
    intent.putExtra(Constant.MOVIE_VOTE_AVG, movie.vote_average)
}
