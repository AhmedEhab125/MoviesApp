package com.example.features.movies.screens.allMoviesScreen

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.base.BaseViewModel
import com.example.ui.model.toUiModel
import com.example.use_case.getPopularMoviesPagingUseCaseImpl.IGetPopularMoviesPagingUseCase
import com.example.use_case.searchMoviesPagingUseCaseImpl.ISearchMoviesPagingUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AllMoviesViewModel(
    private val getPopularMoviesPagingUseCase: IGetPopularMoviesPagingUseCase,
    private val searchMoviesPagingUseCase: ISearchMoviesPagingUseCase
) : BaseViewModel<MoviesState, MoviesEvent, MoviesSideEffect>() {

    private var searchJob: Job? = null

    override fun setInitialState(): MoviesState = MoviesState.Idle

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

    private fun refreshMovies() {
        val currentState = getState()
        if (currentState is MoviesState.DataLoaded && currentState.searchQuery.isNotEmpty()) {
            searchMovies(currentState.searchQuery)
        } else {
            loadPopularMovies()
        }
    }

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