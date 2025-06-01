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


    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getCachedMoviesCount(): Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieCacheEntity>)


    @Query("DELETE FROM movies")
    suspend fun clearMovies()


    @Query("SELECT EXISTS(SELECT 1 FROM movies LIMIT 1)")
    suspend fun hasCachedMovies(): Boolean
} 