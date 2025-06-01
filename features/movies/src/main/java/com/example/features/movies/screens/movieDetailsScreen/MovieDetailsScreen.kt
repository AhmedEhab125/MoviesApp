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

/**
 * Main composable for the Movie Details screen.
 * Handles the display of movie details and manages different UI states.
 *
 * Features:
 * - Displays detailed movie information
 * - Shows loading state while fetching data
 * - Handles and displays errors
 * - Manages navigation effects
 * - Supports Material 3 design
 *
 * @param movieId The unique identifier of the movie to display
 * @param state Current UI state from the ViewModel
 * @param onEvent Callback to handle UI events
 * @param effect Flow of side effects from the ViewModel
 * @param onNavigationRequested Callback to handle navigation requests
 * @param modifier Optional modifier for customizing the layout
 */
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
    // Handle side effects (navigation)
    LaunchedEffect(SideEffectsKey) {
        effect.collect {
            when (it) {
                is MovieDetailsSideEffect.Navigation.NavigateBack -> {
                    onNavigationRequested(it)
                }
            }
        }
    }

    // Handle different states
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
                onRetry = { onEvent(MovieDetailsEvent.LoadMovieDetails(movieId)) },
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

