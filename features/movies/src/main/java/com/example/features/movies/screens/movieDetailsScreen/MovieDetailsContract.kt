package com.example.features.movies.screens.movieDetailsScreen

import com.example.base.ViewEvent
import com.example.base.ViewSideEffect
import com.example.base.ViewState
import com.example.ui.model.MovieUiModel

sealed interface MovieDetailsState : ViewState {
    object Idle : MovieDetailsState
    object Loading : MovieDetailsState
    data class DataLoaded(val movie: MovieUiModel) : MovieDetailsState
    data class Error(val message: String) : MovieDetailsState
}

sealed interface MovieDetailsEvent : ViewEvent {
    data class LoadMovieDetails(val movieId: Int) : MovieDetailsEvent
    data class NavigateTo(val navigation: MovieDetailsSideEffect.Navigation) : MovieDetailsEvent
}

sealed interface MovieDetailsSideEffect : ViewSideEffect {
    sealed class Navigation : MovieDetailsSideEffect {
        object NavigateBack : Navigation()
    }
} 