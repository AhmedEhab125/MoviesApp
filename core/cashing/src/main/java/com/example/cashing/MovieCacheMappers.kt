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
    genreIds = emptyList() // Not cached
)

fun Movie.toCacheEntity(): MovieCacheEntity = MovieCacheEntity(
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
    originalTitle = originalTitle
)
