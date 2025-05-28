package com.example.services.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.models.Movie
import com.example.services.mappers.toDomainModel
import com.example.services.moviesService.IMoviesApiService

class MoviesPagingSource(
    private val moviesService: IMoviesApiService
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1

            val response = moviesService.getPopularMovies(page)
                ?: return LoadResult.Error(Exception("Failed to load movies"))

            LoadResult.Page(
                data = response.results.map { it.toDomainModel() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

class SearchMoviesPagingSource(
    private val moviesService: IMoviesApiService,
    private val query: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1

            val response = moviesService.searchMovies(query, page)
                ?: return LoadResult.Error(Exception("Failed to search movies"))

            LoadResult.Page(
                data = response.results.map { it.toDomainModel() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
} 