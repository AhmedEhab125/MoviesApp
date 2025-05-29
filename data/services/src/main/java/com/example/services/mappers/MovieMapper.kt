package com.example.services.mappers

import com.example.models.Movie
import com.example.models.MoviesModel
import com.example.services.models.MovieResponse
import com.example.services.models.MoviesResponse

fun MovieResponse.toDomainModel(): Movie {
    return Movie(
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
}

fun MoviesResponse.toDomainModel(): MoviesModel {
    return MoviesModel(
        page = page,
        results = results.map { it.toDomainModel() },
        totalPages = totalPages,
        totalResults = totalResults
    )
} 