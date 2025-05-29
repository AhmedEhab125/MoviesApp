package com.example.features.movies.di

import com.example.features.movies.screens.allMoviesScreen.AllMoviesViewModel
import com.example.features.movies.screens.movieDetailsScreen.MovieDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val moviesModule = module {
    viewModel { AllMoviesViewModel(get(), get()) }
    viewModel { MovieDetailsViewModel(get()) }
} 