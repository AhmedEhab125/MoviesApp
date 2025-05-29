package com.example.features.movies.screens.allMoviesScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.example.ui.R
import com.example.ui.components.cards.MovieCard
import com.example.ui.components.loading.LoadingItem
import com.example.ui.components.states.ErrorScreen
import com.example.ui.dimentions.Dimensions
import com.example.ui.model.MovieUiModel


@Composable
fun ColumnMoviesContent(
    lazyPagingItems: LazyPagingItems<MovieUiModel>,
    modifier: Modifier = Modifier,
    onMovieClick: (MovieUiModel) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(Dimensions.dp_16dp)
    ) {
        items(
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.id }
        ) { index ->
            val movie = lazyPagingItems[index]
            if (movie != null) {
                MovieCard(
                    movie = movie,
                    onClick = { onMovieClick(movie) }
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