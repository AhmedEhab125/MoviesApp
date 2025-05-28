package com.example.repositories.di

import com.example.repositories.implementation.MoviesRepositoryImpl
import com.example.repositories_interfaces.IMoviesRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single<IMoviesRepository> { MoviesRepositoryImpl(get()) }
} 