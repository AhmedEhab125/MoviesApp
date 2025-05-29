package com.example.features.movies.screens.allMoviesScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.ui.R
import com.example.ui.components.cards.MovieGridCard
import com.example.ui.components.loading.LoadingItem
import com.example.ui.components.states.ErrorScreen
import com.example.ui.dimentions.Dimensions
import com.example.ui.model.MovieUiModel

@Composable
fun GridMoviesContent(
    lazyPagingItems: LazyPagingItems<MovieUiModel>,
    modifier: Modifier = Modifier,
    onMovieClick: (MovieUiModel) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(NUMBER_OF_COLUMNS),
        verticalArrangement = Arrangement.spacedBy(Dimensions.dp_16dp),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.dp_16dp)
    ) {
        items(
            count = lazyPagingItems.itemCount,
        ) { index ->
            val movie = lazyPagingItems[index]
            if (movie != null) {
                MovieGridCard(
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

private const val NUMBER_OF_COLUMNS = 2