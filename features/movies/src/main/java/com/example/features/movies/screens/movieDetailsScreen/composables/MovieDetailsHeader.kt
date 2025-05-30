package com.example.features.movies.screens.movieDetailsScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.R
import com.example.ui.components.images.NetworkImage
import com.example.ui.constants.UiColors
import com.example.ui.constants.UiConstants
import com.example.ui.dimentions.Dimensions
import com.example.ui.model.MovieUiModel
import com.example.ui.utils.MovieUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsHeader(
    movie: MovieUiModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimensions.dp_400dp)
    ) {
        NetworkImage(
            imageUrl = MovieUtils.getFullPosterUrl(movie.posterUrl),
            contentDescription = MovieUtils.getSafeMovieTitle(movie),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = UiConstants.SURFACE_ALPHA_MINIMAL),
                            Color.Black.copy(alpha = UiConstants.SURFACE_ALPHA_LOW),
                            Color.Black.copy(alpha = UiConstants.SURFACE_ALPHA_MEDIUM)
                        ),
                        startY = UiConstants.GRADIENT_START_Y
                    )
                )
        )

        // Back button
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .padding(Dimensions.dp_8dp)
                        .background(
                            MaterialTheme.colorScheme.surface.copy(alpha = UiConstants.SURFACE_ALPHA_HIGH),
                            RoundedCornerShape(Dimensions.dp_50dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )

        // Movie title and rating at bottom of header
        MovieHeaderInfo(
            movie = movie,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}

@Composable
private fun MovieHeaderInfo(
    movie: MovieUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(Dimensions.dp_16dp)
    ) {
        // Title
        Text(
            text = MovieUtils.getSafeMovieTitle(movie),
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(Dimensions.dp_12dp))

        // Rating and release date row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimensions.dp_12dp)
        ) {
            // Rating
            movie.rating?.let { rating ->
                if (MovieUtils.isValidRating(rating)) {
                    MovieInfoChip(
                        icon = Icons.Default.Star,
                        text = MovieUtils.formatRating(rating),
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = UiConstants.SURFACE_ALPHA_HIGH),
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        iconTint = UiColors.RatingStarGold,
                        contentDescription = stringResource(R.string.cd_movie_rating_chip)
                    )
                }
            }

            // Release date
            movie.releaseDate?.let { releaseDate ->
                MovieInfoChip(
                    icon = Icons.Default.DateRange,
                    text = releaseDate,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = UiConstants.SURFACE_ALPHA_HIGH),
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = stringResource(R.string.cd_movie_release_date_chip)
                )
            }
        }
    }
}

@Composable
private fun MovieInfoChip(
    icon: ImageVector,
    text: String,
    containerColor: Color,
    contentColor: Color,
    iconTint: Color = contentColor,
    contentDescription: String? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(Dimensions.dp_20dp),
        color = containerColor
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = Dimensions.dp_12dp,
                vertical = Dimensions.dp_8dp
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimensions.dp_6dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = iconTint,
                modifier = Modifier.size(Dimensions.dp_16dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = contentColor,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailsHeaderPreview() {
    val sampleMovie = MovieUiModel(
        id = 1,
        title = "Sample Movie",
        posterUrl = "https://example.com/sample-poster.jpg",
        releaseDate = "2023-10-01",
        rating = 8.5,
        overview = "This is a sample movie overview.",
    )

    Surface {
        MovieDetailsHeader(
            movie = sampleMovie,
            onBackClick = {}
        )
    }
}