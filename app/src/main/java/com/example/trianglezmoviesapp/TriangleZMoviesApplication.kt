package com.example.trianglezmoviesapp

import android.app.Application
import com.example.di.coreCashingModule
import com.example.features.movies.di.moviesModule
import com.example.network.di.CoreNetworkModule
import com.example.repositories.di.repositoriesModule
import com.example.services.di.servicesModule
import com.example.use_case.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TriangleZMoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TriangleZMoviesApplication)
            modules(
                CoreNetworkModule,
                servicesModule,
                repositoriesModule,
                useCaseModule,
                moviesModule,
                coreCashingModule
            )
        }
    }
} 