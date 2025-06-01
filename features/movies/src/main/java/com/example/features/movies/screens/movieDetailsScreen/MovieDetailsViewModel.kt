package com.example.features.movies.screens.movieDetailsScreen

import com.example.base.BaseViewModel
import com.example.ui.model.toUiModel
import com.example.use_case.getMovieDetailsUseCase.IGetMovieDetailsUseCase

/**
 * ViewModel for the Movie Details screen that handles displaying detailed information about a specific movie.
 * Extends [BaseViewModel] to implement MVI pattern with state management.
 *
 * Features:
 * - Loads and displays detailed movie information
 * - Handles loading and error states
 * - Manages back navigation
 * - Provides movie metadata like rating, release date, overview
 *
 * @property getMovieDetailsUseCase Use case for fetching detailed movie information
 */
class MovieDetailsViewModel(
    private val getMovieDetailsUseCase: IGetMovieDetailsUseCase
) : BaseViewModel<MovieDetailsState, MovieDetailsEvent, MovieDetailsSideEffect>() {

    /**
     * Sets the initial state of the ViewModel to Idle.
     * This state triggers the initial load of movie details.
     *
     * @return [MovieDetailsState.Idle] as initial state
     */
    override fun setInitialState(): MovieDetailsState = MovieDetailsState.Idle

    /**
     * Handles all UI events and updates state accordingly.
     * Supports loading movie details and navigation events.
     *
     * @param event The UI event to handle
     */
    override fun handleEvents(event: MovieDetailsEvent) {
        when (event) {
            is MovieDetailsEvent.LoadMovieDetails -> loadMovieDetails(event.movieId)
            is MovieDetailsEvent.NavigateTo -> setEffect { event.navigation }
        }
    }

    /**
     * Loads detailed information for a specific movie.
     * Updates state based on loading status and result.
     *
     * Features:
     * - Shows loading state while fetching data
     * - Handles successful data loading
     * - Handles error cases with appropriate error messages
     * - Maps domain model to UI model
     *
     * @param movieId The unique identifier of the movie to load
     */
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