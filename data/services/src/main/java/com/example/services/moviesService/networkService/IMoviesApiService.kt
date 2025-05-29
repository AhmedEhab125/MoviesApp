package com.example.services.moviesService.networkService

import com.example.services.models.MoviesResponse

interface IMoviesApiService {
    suspend fun getPopularMovies(page: Int): MoviesResponse?

    suspend fun searchMovies(query: String, page: Int): MoviesResponse?

}
