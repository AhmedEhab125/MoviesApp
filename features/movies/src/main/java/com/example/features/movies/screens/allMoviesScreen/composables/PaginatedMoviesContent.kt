package com.example.features.movies.screens.allMoviesScreen.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.features.movies.screens.allMoviesScreen.ViewMode
import com.example.ui.R
import com.example.ui.components.loading.LoadingScreen
import com.example.ui.components.states.EmptyState
import com.example.ui.components.states.ErrorScreen
import com.example.ui.model.MovieUiModel

@Composable
fun PaginatedMoviesContent(
    lazyPagingItems: LazyPagingItems<MovieUiModel>,
    viewMode: ViewMode,
    onMovieClick: (MovieUiModel) -> Unit,
    onRetry: () -> Unit
) {
    when (viewMode) {
        ViewMode.LIST -> {
            ColumnMoviesContent(
                lazyPagingItems = lazyPagingItems,
                onMovieClick = onMovieClick,
            )
        }

        ViewMode.GRID -> {
            GridMoviesContent(
                lazyPagingItems = lazyPagingItems,
                onMovieClick = onMovieClick
            )
        }
    }

    // Handle initial loading and error states
    when (lazyPagingItems.loadState.refresh) {
        is LoadState.Loading -> {
            if (lazyPagingItems.itemCount == 0) {
                LoadingScreen(message = stringResource(R.string.loading_movies))
            }
        }

        is LoadState.Error -> {
            if (lazyPagingItems.itemCount == 0) {
                ErrorScreen(
                    error = stringResource(R.string.error_failed_to_load_movies),
                    onRetry = onRetry
                )
            }
        }

        else -> {
            // Show empty state if no items and not loading or error
            if (lazyPagingItems.itemCount == 0) {
                EmptyState(
                    title = stringResource(R.string.empty_no_search_results),
                    subtitle = stringResource(R.string.empty_try_different_keywords)
                )
            }
        }
    }
} 