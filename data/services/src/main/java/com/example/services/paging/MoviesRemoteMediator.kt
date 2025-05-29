package com.example.services.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.cashing.MovieCacheEntity
import com.example.cashing.toCacheEntity
import com.example.services.mappers.toDomainModel
import com.example.services.moviesService.cashingService.IMoviesCacheService
import com.example.services.moviesService.networkService.IMoviesApiService

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val moviesService: IMoviesApiService,
    private val cacheDataSource: IMoviesCacheService
) : RemoteMediator<Int, MovieCacheEntity>() {

    private var lastRequestedPage = 1

    override suspend fun initialize(): InitializeAction {
        // Check if we have cached data and if it's still fresh
        val hasCachedMovies = cacheDataSource.hasCachedMovies()
        if (hasCachedMovies) {
            cacheDataSource.getCachedMoviesCount()
        } else 0

        return if (hasCachedMovies) {
            // If we have cached data, show it first and skip initial refresh
            // Remote data will be fetched on user scroll or manual refresh
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // No cached data, fetch from network immediately
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieCacheEntity>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    lastRequestedPage = 1
                    1
                }

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val nextPage = lastRequestedPage + 1
                    nextPage
                }
            }

            val response = moviesService.getPopularMovies(page)
            val remoteMovies = response?.results?.map {
                it.toDomainModel().toCacheEntity()
            } ?: emptyList()

            val endOfPaginationReached = remoteMovies.isEmpty() ||
                    (response?.totalPages != null && page >= response.totalPages)


            when (loadType) {
                LoadType.REFRESH -> {
                    if (remoteMovies.isNotEmpty()) {
                        // Replace cache with fresh data from first page
                        cacheDataSource.cacheMovies(remoteMovies)
                        lastRequestedPage = page
                    }
                }

                LoadType.APPEND -> {
                    if (remoteMovies.isNotEmpty()) {
                        // Append new data to existing cache
                        cacheDataSource.insertMovies(remoteMovies)
                        lastRequestedPage = page
                    }
                }

                LoadType.PREPEND -> {
                    // Not used for this implementation
                }
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return if (cacheDataSource.hasCachedMovies() && loadType == LoadType.REFRESH) {
                MediatorResult.Success(endOfPaginationReached = false)
            } else {
                MediatorResult.Error(e)
            }
        }
    }
}