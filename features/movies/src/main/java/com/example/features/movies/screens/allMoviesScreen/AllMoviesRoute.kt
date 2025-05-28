package com.example.features.movies.screens.allMoviesScreen

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AllMoviesRoute(
    viewModel: AllMoviesViewModel = koinViewModel(),
    navigateToMovieDetails: (Int) -> Unit
) {
    AllMoviesScreen(
        onEvent = viewModel::setEvent,
        state = viewModel.viewState.collectAsStateWithLifecycle().value,
        effect = viewModel.effect,
        onNavigationRequested = {
            when (it) {
                is MoviesSideEffect.Navigation.NavigateToMovieDetails -> {
                    navigateToMovieDetails(it.movieId)
                }

            }
        },
    )

}