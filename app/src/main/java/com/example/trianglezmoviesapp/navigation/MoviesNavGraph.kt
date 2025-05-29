package com.example.trianglezmoviesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.features.movies.screens.movieDetailsScreen.MovieDetailsRoute
import com.example.features.movies.screens.allMoviesScreen.AllMoviesRoute as AllMoviesScreen

@Composable
fun MoviesNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AllMoviesNavigation
    ) {
        composable<AllMoviesNavigation> {
            AllMoviesScreen(
                navigateToMovieDetails = { movieId ->
                    navController.navigate(MovieDetailsNavigation(movieId))
                }
            )
        }

        composable<MovieDetailsNavigation> { backStackEntry ->
            val movieDetailsNavigation = backStackEntry.toRoute<MovieDetailsNavigation>()
            MovieDetailsRoute(
                movieId = movieDetailsNavigation.movieId,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
} 