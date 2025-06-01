package com.example.features.movies.screens.allMoviesScreen

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.base.BaseViewModel
import com.example.ui.model.toUiModel
import com.example.use_case.getPopularMoviesPagingUseCase.IGetPopularMoviesPagingUseCase
import com.example.use_case.searchMoviesPagingUseCase.ISearchMoviesPagingUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * ViewModel for the All Movies screen that handles both popular movies display and search functionality.
 * Extends [BaseViewModel] to implement MVI pattern with state management.
 *
 * Features:
 * - Displays popular movies with pagination
 * - Implements search functionality with debouncing
 * - Supports list/grid view modes
 * - Handles loading, error, and empty states
 * - Manages navigation to movie details
 *
 * @property getPopularMoviesPagingUseCase Use case for fetching popular movies
 * @property searchMoviesPagingUseCase Use case for searching movies
 */
class AllMoviesViewModel(
    private val getPopularMoviesPagingUseCase: IGetPopularMoviesPagingUseCase,
    private val searchMoviesPagingUseCase: ISearchMoviesPagingUseCase
) : BaseViewModel<MoviesState, MoviesEvent, MoviesSideEffect>() {

    private var searchJob: Job? = null

    override fun setInitialState(): MoviesState = MoviesState.Idle

    /**
     * Handles all UI events and updates state accordingly.
     *
     * @param event The UI event to handle
     */
    override fun handleEvents(event: MoviesEvent) {
        when (event) {
            is MoviesEvent.LoadPopularMovies -> loadPopularMovies()
            is MoviesEvent.RefreshMovies -> refreshMovies()
            is MoviesEvent.SearchMovies -> searchMovies(event.query)
            is MoviesEvent.UpdateSearchQuery -> updateSearchQuery(event.query)
            is MoviesEvent.ClearSearch -> clearSearch()
            is MoviesEvent.ToggleViewMode -> toggleViewMode()
            is MoviesEvent.NavigateTo -> setEffect { event.navigation }
        }
    }

    /**
     * Loads popular movies with pagination support.
     * Updates state with paginated data flow.
     */
    private fun loadPopularMovies() {
        val moviesPagingData =
            getPopularMoviesPagingUseCase()
                .cachedIn(viewModelScope)
                .map {
                    it.map { movie ->
                        movie.toUiModel()
                    }
                }
        setState {
            MoviesState.DataLoaded(
                moviesPagingData = moviesPagingData,
                viewMode = (getState() as? MoviesState.DataLoaded)?.viewMode ?: ViewMode.LIST
            )
        }
    }

    /**
     * Refreshes the current movie list.
     * If in search mode, refreshes search results; otherwise refreshes popular movies.
     */
    private fun refreshMovies() {
        val currentState = getState()
        if (currentState is MoviesState.DataLoaded && currentState.searchQuery.isNotEmpty()) {
            searchMovies(currentState.searchQuery)
        } else {
            loadPopularMovies()
        }
    }

    /**
     * Updates the search query with debouncing.
     * Initiates search after a delay to prevent excessive API calls.
     *
     * @param query The search query string
     */
    private fun updateSearchQuery(query: String) {
        val currentState = getState()
        if (currentState is MoviesState.DataLoaded) {
            setState {
                currentState.copy(searchQuery = query)
            }
        }
        searchJob?.cancel()
        if (query.isNotEmpty()) {
            searchJob = viewModelScope.launch {
                delay(DELAY_TIME)
                searchMovies(query)
            }
        } else {
            clearSearch()
        }
    }

    /**
     * Performs movie search with the given query.
     * Updates state with search results.
     *
     * @param query The search query string
     */
    private fun searchMovies(query: String) {
        if (query.isBlank()) {
            clearSearch()
            return
        }
        val searchPagingData = searchMoviesPagingUseCase(query).cachedIn(viewModelScope).map {
            it.map { movie ->
                movie.toUiModel()
            }
        }
        setState {
            MoviesState.DataLoaded(
                searchPagingData = searchPagingData,
                searchQuery = query,
                viewMode = (getState() as? MoviesState.DataLoaded)?.viewMode ?: ViewMode.LIST,
                isSearching = false
            )
        }
    }

    /**
     * Clears the search state and returns to popular movies display.
     */
    private fun clearSearch() {
        searchJob?.cancel()
        setState {
            MoviesState.DataLoaded(
                searchQuery = "",
                isSearching = false,
                viewMode = (getState() as? MoviesState.DataLoaded)?.viewMode ?: ViewMode.LIST
            )
        }
        loadPopularMovies()
    }

    /**
     * Toggles between list and grid view modes.
     */
    private fun toggleViewMode() {
        val currentState = getState()
        if (currentState is MoviesState.DataLoaded) {
            setState {
                currentState.copy(
                    viewMode = if (currentState.viewMode == ViewMode.LIST) ViewMode.GRID else ViewMode.LIST
                )
            }
        }
    }

    companion object {
        private const val DELAY_TIME = 500L
    }
}