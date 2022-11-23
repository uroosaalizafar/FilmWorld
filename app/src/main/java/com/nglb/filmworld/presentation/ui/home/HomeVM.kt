package com.nglb.filmworld.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nglb.filmworld.data.repository.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(private val repository: MovieRepo) : ViewModel() {
    val list = repository.getAllMovieData().cachedIn(viewModelScope)
}