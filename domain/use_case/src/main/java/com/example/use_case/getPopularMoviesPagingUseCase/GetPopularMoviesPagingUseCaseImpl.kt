package com.example.use_case.getPopularMoviesPagingUseCase

import androidx.paging.PagingData
import com.example.models.Movie
import com.example.repositories_interfaces.IMoviesRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesPagingUseCaseImpl(
    private val moviesRepository: IMoviesRepository
) : IGetPopularMoviesPagingUseCase {
    override operator fun invoke(): Flow<PagingData<Movie>> {
        return moviesRepository.getPopularMoviesPaging()
    }
}