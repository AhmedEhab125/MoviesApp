package com.example.cashing

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    fun pagingSource(): PagingSource<Int, MovieCacheEntity>

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    suspend fun getAllMovies(): List<MovieCacheEntity>

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getCachedMoviesCount(): Int

    @Query("SELECT * FROM movies ORDER BY popularity DESC LIMIT :limit")
    suspend fun getMoviesLimit(limit: Int): List<MovieCacheEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM movies WHERE cacheTimestamp > :timestamp LIMIT 1)")
    suspend fun hasFreshCache(timestamp: Long): Boolean

    @Query("SELECT MAX(cacheTimestamp) FROM movies")
    suspend fun getLastCacheTimestamp(): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieCacheEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieCacheEntity)

    @Query("DELETE FROM movies")
    suspend fun clearMovies()

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteMovie(movieId: Int)

    @Query("DELETE FROM movies WHERE cacheTimestamp < :timestamp")
    suspend fun clearOldCache(timestamp: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM movies LIMIT 1)")
    suspend fun hasCachedMovies(): Boolean
} 