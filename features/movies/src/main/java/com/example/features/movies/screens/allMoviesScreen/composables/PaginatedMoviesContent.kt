package com.example.features.movies.screens.allMoviesScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.example.features.movies.screens.allMoviesScreen.ViewMode
import com.example.ui.R
import com.example.ui.components.cards.MovieCard
import com.example.ui.components.cards.MovieGridCard
import com.example.ui.components.loading.LoadingItem
import com.example.ui.components.loading.LoadingScreen
import com.example.ui.components.states.EmptyState
import com.example.ui.components.states.ErrorScreen
import com.example.ui.dimentions.Dimensions
import com.example.ui.model.MovieUiModel
import com.example.ui.model.toUiModel

@Composable
fun PaginatedMoviesContent(
    lazyPagingItems: LazyPagingItems<com.example.models.Movie>,
    viewMode: ViewMode,
    onMovieClick: (MovieUiModel) -> Unit,
    onRetry: () -> Unit
) {
    when (viewMode) {
        ViewMode.LIST -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Dimensions.dp_16dp)
            ) {
                items(
                    count = lazyPagingItems.itemCount,
                    key = lazyPagingItems.itemKey { it.id }
                ) { index ->
                    val movie = lazyPagingItems[index]
                    if (movie != null) {
                        val uiModel = movie.toUiModel()
                        MovieCard(
                            movie = uiModel,
                            onClick = { onMovieClick(uiModel) }
                        )
                    }
                }

                // Handle loading states
                when (lazyPagingItems.loadState.append) {
                    is LoadState.Loading -> {
                        item {
                            LoadingItem()
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            ErrorScreen(
                                error = stringResource(R.string.error_failed_to_load_more_movies),
                                onRetry = { lazyPagingItems.retry() }
                            )
                        }
                    }

                    else -> {}
                }
            }
        }

        ViewMode.GRID -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(Dimensions.dp_16dp),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.dp_16dp)
            ) {
                items(
                    count = lazyPagingItems.itemCount,
                    key = lazyPagingItems.itemKey { it.id }
                ) { index ->
                    val movie = lazyPagingItems[index]
                    if (movie != null) {
                        val uiModel = movie.toUiModel()
                        MovieGridCard(
                            movie = uiModel,
                            onClick = { onMovieClick(uiModel) }
                        )
                    }
                }

                // Handle loading states
                when (lazyPagingItems.loadState.append) {
                    is LoadState.Loading -> {
                        item {
                            LoadingItem()
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            ErrorScreen(
                                error = stringResource(R.string.error_failed_to_load_more_movies),
                                onRetry = { lazyPagingItems.retry() }
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    // Handle initial loading, error, and empty states
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
            if (lazyPagingItems.itemCount == 0) {
                EmptyState(
                    title = stringResource(R.string.empty_no_movies_found),
                    subtitle = stringResource(R.string.empty_try_refreshing)
                )
            }
        }
    }
} 