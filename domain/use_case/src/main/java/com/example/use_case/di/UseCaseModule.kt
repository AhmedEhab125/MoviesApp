package com.example.use_case.di

import com.example.use_case.getMovieDetailsUseCase.GetMovieDetailsUseCaseImpl
import com.example.use_case.getMovieDetailsUseCase.IGetMovieDetailsUseCase
import com.example.use_case.getPopularMoviesPagingUseCase.GetPopularMoviesPagingUseCaseImpl
import com.example.use_case.getPopularMoviesPagingUseCase.IGetPopularMoviesPagingUseCase
import com.example.use_case.searchMoviesPagingUseCase.ISearchMoviesPagingUseCase
import com.example.use_case.searchMoviesPagingUseCase.SearchMoviesPagingUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<IGetPopularMoviesPagingUseCase> { GetPopularMoviesPagingUseCaseImpl(get()) }
    single<ISearchMoviesPagingUseCase> { SearchMoviesPagingUseCaseImpl(get()) }
    single<IGetMovieDetailsUseCase> { GetMovieDetailsUseCaseImpl(get()) }
} 