package com.example.services.di

import com.example.services.moviesService.cashingService.IMoviesCacheService
import com.example.services.moviesService.cashingService.MoviesCacheService
import com.example.services.moviesService.networkService.IMoviesApiService
import com.example.services.moviesService.networkService.MoviesService
import org.koin.dsl.module

val servicesModule = module {
    single<IMoviesApiService> { MoviesService(get(), get()) }
    single<IMoviesCacheService> { MoviesCacheService(get()) }
} 