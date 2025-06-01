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
import com.example.services.mappers.toDomainModel
import com.example.services.moviesService.cashingService.IMoviesCacheService
import com.example.services.moviesService.networkService.IMoviesApiService
import com.example.services.paging.MoviesRemoteMediator
import com.example.services.paging.SearchMoviesPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [IMoviesRepository] that handles movie data operations.
 * This repository acts as a single source of truth for movie data, managing both network and cache operations.
 *
 * Features:
 * - Pagination support for popular movies with caching
 * - Search functionality with pagination
 * - Movie details fetching
 * - Integration with Room database for offline support
 *
 * @property moviesService Service for handling network operations
 * @property cacheDataSource Service for handling local caching operations
 */
class MoviesRepositoryImpl(
    private val moviesService: IMoviesApiService,
    private val cacheDataSource: IMoviesCacheService
) : IMoviesRepository {

    /**
     * Gets a paginated flow of popular movies with caching support.
     * Uses [MoviesRemoteMediator] to handle pagination and caching strategy.
     *
     * Features:
     * - Configurable page size and prefetch distance
     * - Automatic caching of fetched data
     * - Smooth pagination with RemoteMediator
     *
     * @return Flow of [PagingData] containing [Movie] objects
     */
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

    /**
     * Searches movies based on a query string with pagination support.
     * Uses [SearchMoviesPagingSource] for handling search pagination.
     *
     * Features:
     * - Real-time search results
     * - Configurable page size
     * - Network-only operation (no caching)
     *
     * @param query The search query string
     * @return Flow of [PagingData] containing search results as [Movie] objects
     */
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

    /**
     * Fetches detailed information for a specific movie.
     * Makes a direct API call to get movie details.
     *
     * @param movieId The unique identifier of the movie
     * @return Flow containing the [Movie] details or null if not found
     */
    override fun getMovieDetails(movieId: Int): Flow<Movie?> = flow {
        emit(moviesService.getMovieDetails(movieId)?.toDomainModel())
    }
} 