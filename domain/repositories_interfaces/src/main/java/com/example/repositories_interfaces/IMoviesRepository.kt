package com.example.repositories_interfaces

import androidx.paging.PagingData
import com.example.models.Movie
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {
    fun getPopularMoviesPaging(): Flow<PagingData<Movie>>
    fun searchMoviesPaging(query: String): Flow<PagingData<Movie>>
    fun getMovieDetails(movieId: Int): Flow<Movie?>
} 