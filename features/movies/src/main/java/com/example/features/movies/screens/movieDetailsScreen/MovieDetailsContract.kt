package com.example.features.movies.screens.movieDetailsScreen

import com.example.base.ViewEvent
import com.example.base.ViewSideEffect
import com.example.base.ViewState
import com.example.ui.model.MovieUiModel

/**
 * Contract class containing all the states, events, and side effects for the Movie Details screen.
 * Follows MVI (Model-View-Intent) pattern for predictable state management.
 */

/**
 * Represents the various states of the Movie Details screen.
 * Implements [ViewState] to maintain consistency with the base architecture.
 */
sealed interface MovieDetailsState : ViewState {
    /**
     * Initial state when the screen is first created
     */
    object Idle : MovieDetailsState

    /**
     * State shown while movie details are being loaded
     */
    object Loading : MovieDetailsState

    /**
     * State representing successfully loaded movie details
     * @property movie The movie details to display
     */
    data class DataLoaded(val movie: MovieUiModel) : MovieDetailsState

    /**
     * State shown when an error occurs while loading movie details
     * @property message The error message to display
     */
    data class Error(val message: String) : MovieDetailsState
}

/**
 * Represents all possible events that can be triggered from the Movie Details screen.
 * Implements [ViewEvent] to maintain consistency with the base architecture.
 */
sealed interface MovieDetailsEvent : ViewEvent {
    /**
     * Event triggered to load movie details
     * @property movieId The ID of the movie to load
     */
    data class LoadMovieDetails(val movieId: Int) : MovieDetailsEvent

    /**
     * Event triggered for navigation actions
     * @property navigation The navigation action to perform
     */
    data class NavigateTo(val navigation: MovieDetailsSideEffect.Navigation) : MovieDetailsEvent
}

/**
 * Represents one-time side effects that can occur in the Movie Details screen.
 * Implements [ViewSideEffect] to maintain consistency with the base architecture.
 */
sealed interface MovieDetailsSideEffect : ViewSideEffect {
    /**
     * Navigation-related side effects
     */
    sealed class Navigation : MovieDetailsSideEffect {
        /**
         * Effect to navigate back to the previous screen
         */
        object NavigateBack : Navigation()
    }
} 