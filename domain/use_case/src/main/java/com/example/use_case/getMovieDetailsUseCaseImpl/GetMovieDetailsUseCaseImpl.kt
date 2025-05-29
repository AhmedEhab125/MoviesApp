package com.example.use_case.getMovieDetailsUseCaseImpl

import com.example.models.Movie
import com.example.repositories_interfaces.IMoviesRepository
import kotlinx.coroutines.flow.Flow

class GetMovieDetailsUseCaseImpl(
    private val moviesRepository: IMoviesRepository
) : IGetMovieDetailsUseCase {
    override operator fun invoke(movieId: Int): Flow<Movie?> {
        return moviesRepository.getMovieDetails(movieId)
    }
} 