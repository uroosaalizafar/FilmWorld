package com.nglb.filmworld.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.nglb.filmworld.data.network.MovieApiInterface
import com.nglb.filmworld.data.pagingSource.MoviePagingSource
import com.nglb.filmworld.data.pagingSource.SearchPagingSource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MovieRepo @Inject constructor(private val movieApiInterface: MovieApiInterface) {

    fun getSearchMovie(queryString: String) = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { SearchPagingSource(movieApiInterface, queryString) }
    ).liveData

    fun getAllMovieData() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { MoviePagingSource(movieApiInterface) }
    ).liveData


}