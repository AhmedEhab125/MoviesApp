package com.example.cashing

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Constants.TABLE_NAME)
data class MovieCacheEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val adult: Boolean,
    val originalLanguage: String,
    val originalTitle: String,
    val cacheTimestamp: Long = System.currentTimeMillis()
) 