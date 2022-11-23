package com.nglb.filmworld.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nglb.filmworld.data.model.Movie
import com.nglb.filmworld.data.network.MovieApiInterface
import com.nglb.filmworld.utils.Constant
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource (
    private val movieApiInterface: MovieApiInterface,
) :
    PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: FIRST_PAGE_INDEX
            val response = movieApiInterface.getAllMovie(
                api_key = Constant.API_KEY,
                page = position,
            )
            if (response.isSuccessful) {
                LoadResult.Page(
                    data = response.body()!!.results,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (position == response.body()!!.total_pages) null else position + 1
                )
            } else {
                throw IOException("Failed fetch data")
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: IOException) {
            return LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }

}