package com.example.features.movies.screens.movieDetailsScreen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.base.SideEffectsKey
import com.example.features.movies.screens.movieDetailsScreen.composables.MovieDetailsContent
import com.example.ui.R
import com.example.ui.components.loading.LoadingScreen
import com.example.ui.components.states.ErrorScreen
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    movieId: Int,
    state: MovieDetailsState,
    onEvent: (MovieDetailsEvent) -> Unit,
    effect: Flow<MovieDetailsSideEffect>,
    onNavigationRequested: (MovieDetailsSideEffect.Navigation) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(SideEffectsKey) {
        effect.collect {
            when (it) {
                is MovieDetailsSideEffect.Navigation.NavigateBack -> {
                    onNavigationRequested(it)
                }
            }
        }
    }

    when (state) {
        is MovieDetailsState.Idle -> {
            onEvent(MovieDetailsEvent.LoadMovieDetails(movieId))

        }

        is MovieDetailsState.Loading -> {
            LoadingScreen(message = stringResource(R.string.loading_movie_details))
        }

        is MovieDetailsState.Error -> {
            ErrorScreen(
                error = state.message,
                onRetry = { /* Will be handled by navigation */ }
            )
        }

        is MovieDetailsState.DataLoaded -> {
            MovieDetailsContent(
                movie = state.movie,
                onBackClick = { onEvent(MovieDetailsEvent.NavigateTo(MovieDetailsSideEffect.Navigation.NavigateBack)) },
                modifier = modifier
            )
        }
    }
}

