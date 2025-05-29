package com.example.cashing

import androidx.paging.PagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesCacheDataSource(private val movieDao: MovieDao) {
    fun getCachedMoviesPagination(): PagingSource<Int, MovieCacheEntity> = movieDao.pagingSource()


    suspend fun getCachedMoviesCount(): Int = withContext(Dispatchers.IO) {
        movieDao.getCachedMoviesCount()
    }


    suspend fun hasCachedMovies(): Boolean = withContext(Dispatchers.IO) {
        movieDao.hasCachedMovies()
    }

    suspend fun clearAndInsertMovies(movies: List<MovieCacheEntity>) = withContext(Dispatchers.IO) {
        movieDao.clearMovies()
        if (movies.isNotEmpty()) {
            movieDao.insertMovies(movies)
        }
    }

    suspend fun insertMovies(movies: List<MovieCacheEntity>) = withContext(Dispatchers.IO) {
        if (movies.isNotEmpty()) {
            movieDao.insertMovies(movies)
        }
    }

} 