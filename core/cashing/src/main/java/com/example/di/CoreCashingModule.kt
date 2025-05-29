package com.example.di

import android.app.Application
import androidx.room.Room
import com.example.cashing.Constants
import com.example.cashing.MovieDatabase
import com.example.cashing.MoviesCacheDataSource
import org.koin.dsl.module

val coreCashingModule = module {
    single {
        Room.databaseBuilder(get<Application>(), MovieDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration(dropAllTables = false)
            .build()
    }
    single { get<MovieDatabase>().movieDao() }
    single { MoviesCacheDataSource(get()) }
}