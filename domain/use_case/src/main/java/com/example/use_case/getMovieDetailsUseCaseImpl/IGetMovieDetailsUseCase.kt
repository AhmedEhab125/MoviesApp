package com.example.use_case.getMovieDetailsUseCaseImpl

import com.example.models.Movie
import kotlinx.coroutines.flow.Flow

interface IGetMovieDetailsUseCase {
    operator fun invoke(movieId: Int): Flow<Movie?>
} 