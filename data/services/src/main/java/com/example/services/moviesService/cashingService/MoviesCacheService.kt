package com.example.services.moviesService.cashingService

import androidx.paging.PagingSource
import com.example.cashing.MovieCacheEntity
import com.example.cashing.MoviesCacheDataSource

internal class MoviesCacheService(private val cacheDataSource: MoviesCacheDataSource) :
    IMoviesCacheService {
    override suspend fun insertMovies(movies: List<MovieCacheEntity>) {
        cacheDataSource.insertMovies(movies)
    }

    override suspend fun cacheMovies(movies: List<MovieCacheEntity>) {
        cacheDataSource.clearAndInsertMovies(movies)
    }

    override suspend fun getCachedMoviesCount(): Int =
        cacheDataSource.getCachedMoviesCount()

    override suspend fun hasCachedMovies(): Boolean =
        cacheDataSource.hasCachedMovies()


    override fun getCachedMoviesPagination(): PagingSource<Int, MovieCacheEntity> =
        cacheDataSource.getCachedMoviesPagination()
} 