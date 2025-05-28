package com.example.use_case.searchMoviesPagingUseCaseImpl

import androidx.paging.PagingData
import com.example.models.Movie
import com.example.repositories_interfaces.IMoviesRepository
import kotlinx.coroutines.flow.Flow

class SearchMoviesPagingUseCaseImpl(
    private val moviesRepository: IMoviesRepository
) : ISearchMoviesPagingUseCase {
    override operator fun invoke(query: String): Flow<PagingData<Movie>> {
        return moviesRepository.searchMoviesPaging(query)
    }
} 