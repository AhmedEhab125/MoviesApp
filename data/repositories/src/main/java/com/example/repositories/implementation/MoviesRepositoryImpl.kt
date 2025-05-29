package com.example.repositories.implementation

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.cashing.toDomainModel
import com.example.models.Movie
import com.example.repositories_interfaces.IMoviesRepository
import com.example.services.ApiConstants
import com.example.services.moviesService.cashingService.IMoviesCacheService
import com.example.services.moviesService.networkService.IMoviesApiService
import com.example.services.paging.MoviesRemoteMediator
import com.example.services.paging.SearchMoviesPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class MoviesRepositoryImpl(
    private val moviesService: IMoviesApiService,
    private val cacheDataSource: IMoviesCacheService
) : IMoviesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPopularMoviesPaging(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = ApiConstants.PREFETCH_DISTANCE,
                initialLoadSize = ApiConstants.INITIAL_LOAD_SIZE
            ),
            remoteMediator = MoviesRemoteMediator(moviesService, cacheDataSource),
            pagingSourceFactory = { cacheDataSource.getCachedMoviesPagination() }
        ).flow.map {
            it.map { movieCacheEntity ->
                movieCacheEntity.toDomainModel()
            }
        }
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