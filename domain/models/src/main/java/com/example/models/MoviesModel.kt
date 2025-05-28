package com.example.models

data class MoviesModel(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
) 