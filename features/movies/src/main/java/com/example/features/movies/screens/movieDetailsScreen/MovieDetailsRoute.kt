package com.example.features.movies.screens.movieDetailsScreen

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MovieDetailsRoute(
    movieId: Int,
    viewModel: MovieDetailsViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {
    MovieDetailsScreen(
        movieId = movieId,
        state = viewModel.viewState.collectAsStateWithLifecycle().value,
        onEvent = viewModel::setEvent,
        effect = viewModel.effect,
        onNavigationRequested = { navigation ->
            when (navigation) {
                is MovieDetailsSideEffect.Navigation.NavigateBack -> {
                    navigateBack()
                }
            }
        }
    )
} 