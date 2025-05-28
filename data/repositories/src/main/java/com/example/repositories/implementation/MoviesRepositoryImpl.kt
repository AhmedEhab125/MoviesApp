package com.example.repositories.implementation

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.models.Movie
import com.example.repositories_interfaces.IMoviesRepository
import com.example.services.ApiConstants
import com.example.services.moviesService.IMoviesApiService
import com.example.services.paging.MoviesPagingSource
import com.example.services.paging.SearchMoviesPagingSource
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl(
    private val moviesService: IMoviesApiService
) : IMoviesRepository {

    override fun getPopularMoviesPaging(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = ApiConstants.INITIAL_LOAD_SIZE
            ),
            pagingSourceFactory = { MoviesPagingSource(moviesService) }
        ).flow
    }

    override fun searchMoviesPaging(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = ApiConstants.INITIAL_LOAD_SIZE
            ),
            pagingSourceFactory = { SearchMoviesPagingSource(moviesService, query) }
        ).flow
    }
} 