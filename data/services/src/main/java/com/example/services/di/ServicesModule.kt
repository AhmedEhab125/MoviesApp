package com.example.services.di

import com.example.services.moviesService.IMoviesApiService
import com.example.services.moviesService.MoviesService
import org.koin.dsl.module

val servicesModule = module {
    single<IMoviesApiService> { MoviesService(get(), get()) }
} 