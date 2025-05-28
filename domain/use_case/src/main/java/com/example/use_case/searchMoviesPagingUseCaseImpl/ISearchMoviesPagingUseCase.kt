package com.example.use_case.searchMoviesPagingUseCaseImpl

import androidx.paging.PagingData
import com.example.models.Movie
import kotlinx.coroutines.flow.Flow

interface ISearchMoviesPagingUseCase {
    operator fun invoke(query: String): Flow<PagingData<Movie>>
} 