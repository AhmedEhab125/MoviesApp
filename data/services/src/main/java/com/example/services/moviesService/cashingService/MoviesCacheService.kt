package com.example.services.moviesService.cashingService

import androidx.paging.PagingSource
import com.example.cashing.MovieCacheEntity
import com.example.cashing.MoviesCacheDataSource

/**
 * Service class that handles local caching operations for movies.
 * Implements [IMoviesCacheService] to provide caching functionality using Room database.
 *
 * Features:
 * - Manages movie data persistence
 * - Handles cache invalidation and updates
 * - Provides paginated access to cached data
 * - Supports offline-first architecture
 *
 * @property cacheDataSource Data source for accessing the local cache database
 */
internal class MoviesCacheService(private val cacheDataSource: MoviesCacheDataSource) :
    IMoviesCacheService {

    /**
     * Inserts a list of movies into the cache without clearing existing data.
     * Useful for appending new pages of data.
     *
     * @param movies List of [MovieCacheEntity] to be inserted
     */
    override suspend fun insertMovies(movies: List<MovieCacheEntity>) {
        cacheDataSource.insertMovies(movies)
    }

    /**
     * Clears the existing cache and inserts new movies.
     * Useful for refreshing the entire dataset.
     *
     * @param movies List of [MovieCacheEntity] to replace existing cache
     */
    override suspend fun cacheMovies(movies: List<MovieCacheEntity>) {
        cacheDataSource.clearAndInsertMovies(movies)
    }

    /**
     * Gets the total count of movies in the cache.
     *
     * @return Number of cached movies
     */
    override suspend fun getCachedMoviesCount(): Int =
        cacheDataSource.getCachedMoviesCount()

    /**
     * Checks if there are any movies in the cache.
     *
     * @return true if cache contains movies, false otherwise
     */
    override suspend fun hasCachedMovies(): Boolean =
        cacheDataSource.hasCachedMovies()

    /**
     * Gets a [PagingSource] for accessing cached movies with pagination.
     * Used by the Paging 3 library for efficient data loading.
     *
     * @return [PagingSource] for cached movies
     */
    override fun getCachedMoviesPagination(): PagingSource<Int, MovieCacheEntity> =
        cacheDataSource.getCachedMoviesPagination()
} 