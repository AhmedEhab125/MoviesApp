package com.example.use_case.getPopularMoviesPagingUseCase

import androidx.paging.PagingData
import com.example.models.Movie
import kotlinx.coroutines.flow.Flow

interface IGetPopularMoviesPagingUseCase {
    operator fun invoke(): Flow<PagingData<Movie>>
} 