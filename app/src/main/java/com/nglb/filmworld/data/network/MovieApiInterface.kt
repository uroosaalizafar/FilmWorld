package com.nglb.filmworld.data.network

import com.nglb.filmworld.data.model.MovieResponse
import com.nglb.filmworld.data.model.LatestMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// retrofit interface to add endpoint of the url
interface MovieApiInterface {

    @GET("discover/movie")
    suspend fun getAllMovie(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
    ): Response<MovieResponse>

    @GET("search/movie")
    suspend fun getSearchMovie(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("query") query: String,
    ): Response<MovieResponse>

    @GET("movie/latest")
    suspend fun getLatestMovie(
        @Query("api_key") api_key: String,
    ): Response<LatestMovie>
}