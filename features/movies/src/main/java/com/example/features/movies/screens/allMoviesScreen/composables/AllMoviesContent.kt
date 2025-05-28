package com.example.features.movies.screens.allMoviesScreen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.features.movies.screens.allMoviesScreen.MoviesState
import com.example.models.Movie
import com.example.ui.R
import com.example.ui.components.search.MovieSearchBar
import com.example.ui.components.states.EmptyState
import com.example.ui.dimentions.Dimensions
import com.example.ui.model.MovieUiModel

@Composable
fun AllMoviesContent(
    modifier: Modifier = Modifier,
    state: MoviesState.DataLoaded,
    onChangeModeClicked: () -> Unit,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    onRefreshMovies: () -> Unit,
    onRetrySearch: () -> Unit,
    onMovieClick: (MovieUiModel) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimensions.dp_16dp)
    ) {
        AllMoviesHeader(
            state = state,
            onChangeModeClicked = onChangeModeClicked,
        )
        MovieSearchBar(
            query = state.searchQuery,
            onQueryChange = onQueryChanged,
            onSearch = onSearch,
            modifier = Modifier.padding(bottom = Dimensions.dp_16dp)
        )
        when {
            state.searchQuery.isNotEmpty() && state.searchPagingData != null -> {
                val searchLazyPagingItems = state.searchPagingData.collectAsLazyPagingItems()
                PaginatedMoviesContent(
                    lazyPagingItems = searchLazyPagingItems,
                    viewMode = state.viewMode,
                    onMovieClick = onMovieClick,
                    onRetry = { onRetrySearch() }
                )
            }

            state.moviesPagingData != null -> {
                val lazyPagingItems = state.moviesPagingData.collectAsLazyPagingItems()
                PaginatedMoviesContent(
                    lazyPagingItems = lazyPagingItems,
                    viewMode = state.viewMode,
                    onMovieClick = { /* TODO: Navigate to movie details */ },
                    onRetry = { onRefreshMovies() }
                )
            }

            else -> {
                EmptyState(
                    title = stringResource(R.string.empty_no_movies_found),
                    subtitle = stringResource(R.string.empty_try_refreshing)
                )
            }
        }
    }
}