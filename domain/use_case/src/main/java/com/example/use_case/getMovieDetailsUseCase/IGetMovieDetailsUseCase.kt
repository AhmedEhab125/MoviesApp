package com.example.use_case.getMovieDetailsUseCase

import com.example.models.Movie
import kotlinx.coroutines.flow.Flow

interface IGetMovieDetailsUseCase {
    operator fun invoke(movieId: Int): Flow<Movie?>
} 