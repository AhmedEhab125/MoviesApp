package com.example.use_case.di

import com.example.use_case.getMovieDetailsUseCaseImpl.GetMovieDetailsUseCaseImpl
import com.example.use_case.getMovieDetailsUseCaseImpl.IGetMovieDetailsUseCase
import com.example.use_case.getPopularMoviesPagingUseCaseImpl.GetPopularMoviesPagingUseCaseImpl
import com.example.use_case.getPopularMoviesPagingUseCaseImpl.IGetPopularMoviesPagingUseCase
import com.example.use_case.searchMoviesPagingUseCaseImpl.ISearchMoviesPagingUseCase
import com.example.use_case.searchMoviesPagingUseCaseImpl.SearchMoviesPagingUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<IGetPopularMoviesPagingUseCase> { GetPopularMoviesPagingUseCaseImpl(get()) }
    single<ISearchMoviesPagingUseCase> { SearchMoviesPagingUseCaseImpl(get()) }
    single<IGetMovieDetailsUseCase> { GetMovieDetailsUseCaseImpl(get()) }
} 