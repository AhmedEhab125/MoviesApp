package com.example.features.movies.screens.movieDetailsScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.R
import com.example.ui.constants.UiColors
import com.example.ui.constants.UiConstants
import com.example.ui.dimentions.Dimensions
import com.example.ui.model.MovieUiModel
import com.example.ui.utils.MovieUtils

@Composable
fun MovieDetailsContentSection(
    movie: MovieUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.dp_16dp),
        verticalArrangement = Arrangement.spacedBy(Dimensions.dp_20dp)
    ) {
        MovieQuickStatsCard(movie = movie)

        movie.overview?.let { overview ->
            MovieOverviewCard(overview = overview)
        }

        Spacer(modifier = Modifier.height(Dimensions.dp_16dp))
    }
}

@Composable
private fun MovieQuickStatsCard(
    movie: MovieUiModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimensions.dp_16dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimensions.dp_4dp)
    ) {
        Column(
            modifier = Modifier.padding(Dimensions.dp_20dp)
        ) {
            Text(
                text = stringResource(R.string.movie_details_information),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(Dimensions.dp_16dp))

            // Stats grid
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimensions.dp_12dp)
            ) {
                movie.rating?.let { rating ->
                    if (MovieUtils.isValidRating(rating)) {
                        MovieStatItem(
                            icon = Icons.Default.Star,
                            label = stringResource(R.string.movie_details_rating),
                            value = stringResource(R.string.movie_details_rating_format, rating),
                            iconTint = UiColors.RatingStarGold
                        )
                    }
                }

                movie.releaseDate?.let { releaseDate ->
                    MovieStatItem(
                        icon = Icons.Default.DateRange,
                        label = stringResource(R.string.movie_details_release_date),
                        value = releaseDate,
                        iconTint = MaterialTheme.colorScheme.primary
                    )
                }

                // Movie quality indicator
                movie.rating?.let { rating ->
                    if (MovieUtils.isValidRating(rating)) {
                        val (qualityText, qualityColor) = getMovieQuality(rating)

                        MovieStatItem(
                            icon = Icons.Default.ThumbUp,
                            label = stringResource(R.string.movie_details_quality),
                            value = stringResource(qualityText),
                            iconTint = qualityColor,
                            contentDescription = stringResource(R.string.cd_movie_quality_indicator)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun getMovieQuality(rating: Double): Pair<Int, Color> {
    return when {
        rating >= UiConstants.RATING_EXCELLENT_THRESHOLD -> Pair(
            R.string.movie_quality_excellent,
            UiColors.QualityExcellent
        )

        rating >= UiConstants.RATING_VERY_GOOD_THRESHOLD -> Pair(
            R.string.movie_quality_very_good,
            UiColors.QualityVeryGood
        )

        rating >= UiConstants.RATING_GOOD_THRESHOLD -> Pair(
            R.string.movie_quality_good,
            UiColors.QualityGood
        )

        rating >= UiConstants.RATING_AVERAGE_THRESHOLD -> Pair(
            R.string.movie_quality_average,
            UiColors.QualityAverage
        )

        else -> Pair(R.string.movie_quality_below_average, UiColors.QualityBelowAverage)
    }
}

@Composable
private fun MovieStatItem(
    icon: ImageVector,
    label: String,
    value: String,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    contentDescription: String? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimensions.dp_12dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = iconTint,
                modifier = Modifier.size(Dimensions.dp_20dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun MovieOverviewCard(
    overview: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimensions.dp_16dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimensions.dp_4dp)
    ) {
        Column(
            modifier = Modifier.padding(Dimensions.dp_20dp)
        ) {
            Text(
                text = stringResource(R.string.movie_details_synopsis),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(Dimensions.dp_16dp))

            Text(
                text = overview,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Justify,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight.times(UiConstants.TEXT_LINE_HEIGHT_MULTIPLIER)
            )
        }
    }
}

@Preview
@Composable
private fun MovieDetailsContentSectionPreview() {
    val sampleMovie = MovieUiModel(
        id = 1,
        title = "Sample Movie",
        overview = "This is a sample movie overview for preview purposes.",
        posterUrl = null,
        releaseDate = "2023-10-01",
        rating = 7.5
    )

    MovieDetailsContentSection(movie = sampleMovie)
}