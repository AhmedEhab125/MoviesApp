package com.example.ui.constants

object UiConstants {
    // Loading messages
    const val LOADING_MOVIES = "Loading movies..."

    // Image URLs
    const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    const val TMDB_IMAGE_BASE_URL_ORIGINAL = "https://image.tmdb.org/t/p/original"
    const val TMDB_IMAGE_BASE_URL_W780 = "https://image.tmdb.org/t/p/w780"

    // UI dimensions
    const val CARD_CORNER_RADIUS = 16

    // Movie rating thresholds
    const val RATING_EXCELLENT_THRESHOLD = 8.0
    const val RATING_VERY_GOOD_THRESHOLD = 7.0
    const val RATING_GOOD_THRESHOLD = 6.0
    const val RATING_AVERAGE_THRESHOLD = 5.0

    // Rating constraints
    const val MIN_RATING = 0.0
    const val MAX_RATING = 10.0


    // Gradient constants
    const val GRADIENT_START_Y = 0.2f
    const val SURFACE_ALPHA_HIGH = 0.95f
    const val SURFACE_ALPHA_MEDIUM = 0.8f
    const val SURFACE_ALPHA_LOW = 0.4f
    const val SURFACE_ALPHA_MINIMAL = 0.1f

    // Line height multiplier for better readability
    const val TEXT_LINE_HEIGHT_MULTIPLIER = 1.2f

} 