package com.nglb.filmworld.presentation.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nglb.filmworld.data.repository.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchMovieVM @Inject constructor(
    private val repo: MovieRepo,
    state: SavedStateHandle
) : ViewModel() {
    private val searchMoviequery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val searchMovie = searchMoviequery.switchMap { queryString ->
        repo.getSearchMovie(queryString).cachedIn(viewModelScope)
    }

    fun searchMovieString(query: String) {
        searchMoviequery.value = query
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "Black"
    }

}