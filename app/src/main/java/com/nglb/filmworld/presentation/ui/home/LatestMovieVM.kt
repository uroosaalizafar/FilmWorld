package com.nglb.filmworld.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nglb.filmworld.data.repository.Repo
import com.nglb.filmworld.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class LatestMovieVM @Inject constructor(
    private val mainRepository: Repo
) : ViewModel() {
    fun fetchLatestMovie(apiKey: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.getUsers(apiKey)))
        } catch (exception: Exception) {
            emit(Resource.error(exception.message ?: "Error Occurred!", data = null))
        }
    }
}