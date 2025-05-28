package com.example.ui.model

import com.example.models.Movie

// UI model for displaying movie info in the UI
data class MovieUiModel(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val releaseDate: String?,
    val rating: Double?,
    val overview: String?
)

// Extension function to map from domain model to UI model
fun Movie.toUiModel(): MovieUiModel = MovieUiModel(
    id = id,
    title = title,
    posterUrl = posterPath,
    releaseDate = releaseDate,
    rating = voteAverage,
    overview = overview
) 