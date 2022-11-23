package com.nglb.filmworld.data.repository

import com.nglb.filmworld.data.network.MovieApiInterface
import com.nglb.filmworld.data.model.LatestMovie
import retrofit2.Response
import javax.inject.Inject

class Repo @Inject constructor(private val apiService: MovieApiInterface) {
    suspend fun getUsers(apiKey:String): Response<LatestMovie> {
        return apiService.getLatestMovie(apiKey)
    }
}