package com.example.services.moviesService.cashingService

import androidx.paging.PagingSource
import com.example.cashing.MovieCacheEntity

interface IMoviesCacheService {
    suspend fun insertMovies(movies: List<MovieCacheEntity>)
    suspend fun cacheMovies(movies: List<MovieCacheEntity>)
    suspend fun getCachedMoviesCount(): Int
    suspend fun hasCachedMovies(): Boolean
    fun getCachedMoviesPagination(): PagingSource<Int, MovieCacheEntity>
}