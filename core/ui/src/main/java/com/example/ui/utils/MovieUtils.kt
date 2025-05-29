package com.example.ui.utils

import com.example.ui.constants.UiConstants
import com.example.ui.model.MovieUiModel

/**
 * Utility functions for movie-related operations
 */
object MovieUtils {

    /**
     * Validates if a rating is within valid range
     */
    fun isValidRating(rating: Double?): Boolean {
        return rating != null && rating >= UiConstants.MIN_RATING && rating <= UiConstants.MAX_RATING
    }

    /**
     * Safely formats a rating value
     */
    fun formatRating(rating: Double?): String {
        return if (isValidRating(rating)) {
            String.format("%.1f", rating)
        } else {
            "N/A"
        }
    }

    /**
     * Gets the full image URL for a poster path
     */
    fun getFullPosterUrl(posterPath: String?): String? {
        return posterPath?.let { "${UiConstants.TMDB_IMAGE_BASE_URL_W780}$it" }
    }


    /**
     * Gets a safe movie title (fallback to "Unknown Movie" if empty)
     */
    fun getSafeMovieTitle(movie: MovieUiModel): String {
        return movie.title.ifBlank { "Unknown Movie" }
    }
} 