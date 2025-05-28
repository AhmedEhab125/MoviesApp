package com.example.features.movies.screens.allMoviesScreen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.base.SideEffectsKey
import com.example.features.movies.screens.allMoviesScreen.composables.AllMoviesContent
import com.example.ui.R
import com.example.ui.components.loading.LoadingScreen
import com.example.ui.components.states.ErrorScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllMoviesScreen(
    state: MoviesState,
    onEvent: (MoviesEvent) -> Unit,
    effect: Flow<MoviesSideEffect>,
    onNavigationRequested: (MoviesSideEffect.Navigation) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(SideEffectsKey) {
        effect.onEach {
            when (it) {
                is MoviesSideEffect.Navigation.NavigateToMovieDetails -> {
                    onNavigationRequested(it)
                }

                is MoviesSideEffect.ShowError -> TODO()
            }
        }
    }
    when (state) {
        is MoviesState.Idle -> {
            onEvent(MoviesEvent.LoadPopularMovies)
        }

        is MoviesState.Loading -> {
            LoadingScreen(message = stringResource(R.string.loading_popular_movies))
        }

        is MoviesState.Error -> {
            ErrorScreen(
                error = state.message,
                onRetry = { onEvent(MoviesEvent.RefreshMovies) }
            )
        }

        is MoviesState.DataLoaded -> {
            AllMoviesContent(
                modifier = modifier,
                state = state,
                onChangeModeClicked = { onEvent(MoviesEvent.ToggleViewMode) },
                onQueryChanged = { onEvent(MoviesEvent.UpdateSearchQuery(it)) },
                onSearch = { onEvent(MoviesEvent.SearchMovies(it)) },
                onRefreshMovies = { onEvent(MoviesEvent.RefreshMovies) },
                onRetrySearch = { onEvent(MoviesEvent.SearchMovies(state.searchQuery)) },
                onMovieClick = { movie ->
                    onEvent(
                        MoviesEvent.NavigateTo(
                            MoviesSideEffect.Navigation.NavigateToMovieDetails(
                                movie.id
                            )
                        )
                    )
                }
            )
        }
    }
}

