package com.example.features.movies.screens.allMoviesScreen

import androidx.paging.PagingData
import com.example.base.ViewEvent
import com.example.base.ViewSideEffect
import com.example.base.ViewState
import com.example.ui.model.MovieUiModel
import kotlinx.coroutines.flow.Flow

sealed interface MoviesState : ViewState {
    object Idle : MoviesState
    object Loading : MoviesState
    data class DataLoaded(
        val moviesPagingData: Flow<PagingData<MovieUiModel>>? = null,
        val searchPagingData: Flow<PagingData<MovieUiModel>>? = null,
        val searchQuery: String = "",
        val isSearching: Boolean = false,
        val viewMode: ViewMode = ViewMode.LIST
    ) : MoviesState

    data class Error(val message: String) : MoviesState
}

enum class ViewMode {
    LIST, GRID
}

sealed interface MoviesEvent : ViewEvent {
    object LoadPopularMovies : MoviesEvent
    object RefreshMovies : MoviesEvent
    data class SearchMovies(val query: String) : MoviesEvent
    data class UpdateSearchQuery(val query: String) : MoviesEvent
    object ClearSearch : MoviesEvent
    object ToggleViewMode : MoviesEvent
    data class NavigateTo(val navigation: MoviesSideEffect.Navigation) : MoviesEvent
}

sealed interface MoviesSideEffect : ViewSideEffect {
    sealed class Navigation : MoviesSideEffect {
        data class NavigateToMovieDetails(val movieId: Int) : Navigation()
    }
}