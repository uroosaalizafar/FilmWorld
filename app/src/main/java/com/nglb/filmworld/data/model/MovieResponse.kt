package com.nglb.filmworld.data.model

//hold the data of Movie Object and movie response
data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)