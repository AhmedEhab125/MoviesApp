package com.example.cashing

import com.example.models.Movie


fun MovieCacheEntity.toDomainModel(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    popularity = popularity,
    adult = adult,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
)

fun Movie.toCacheEntity(): MovieCacheEntity = MovieCacheEntity(
    id = id ?: -1,
    title = title ?: "",
    overview = overview ?: "",
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate ?: "",
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0,
    popularity = popularity ?: 0.0,
    adult = adult ?: false,
    originalLanguage = originalLanguage ?: "",
    originalTitle = originalTitle ?: ""
)
