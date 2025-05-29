package com.example.trianglezmoviesapp.navigation

import kotlinx.serialization.Serializable

@Serializable
object AllMoviesNavigation

@Serializable
data class MovieDetailsNavigation(val movieId: Int)