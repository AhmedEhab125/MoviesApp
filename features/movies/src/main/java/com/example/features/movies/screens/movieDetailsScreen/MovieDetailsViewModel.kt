package com.example.features.movies.screens.movieDetailsScreen

import com.example.base.BaseViewModel
import com.example.ui.model.toUiModel
import com.example.use_case.getMovieDetailsUseCaseImpl.IGetMovieDetailsUseCase

class MovieDetailsViewModel(
    private val getMovieDetailsUseCase: IGetMovieDetailsUseCase
) : BaseViewModel<MovieDetailsState, MovieDetailsEvent, MovieDetailsSideEffect>() {

    override fun setInitialState(): MovieDetailsState = MovieDetailsState.Idle

    override fun handleEvents(event: MovieDetailsEvent) {
        when (event) {
            is MovieDetailsEvent.LoadMovieDetails -> loadMovieDetails(event.movieId)
            is MovieDetailsEvent.NavigateTo -> setEffect { event.navigation }
        }
    }

    private fun loadMovieDetails(movieId: Int) {
        setState { MovieDetailsState.Loading }

        getMovieDetailsUseCase(movieId).launchAndCollectResult(
            onStart = {
                setState { MovieDetailsState.Loading }
            },
            onSuccess = { movie ->
                if (movie != null) {
                    setState {
                        MovieDetailsState.DataLoaded(movie.toUiModel())
                    }
                }
            },
            onError = { error ->
                setState { MovieDetailsState.Error(error.message ?: "Unknown error") }
            },
        )
    }
} 