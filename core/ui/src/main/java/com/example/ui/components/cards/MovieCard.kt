package com.example.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.R
import com.example.ui.components.images.NetworkImage
import com.example.ui.constants.AspectRatios
import com.example.ui.constants.UiConstants
import com.example.ui.dimentions.Dimensions
import com.example.ui.model.MovieUiModel

@Composable
fun MovieCard(
    movie: MovieUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = Dimensions.dp_8dp),
        shape = RoundedCornerShape(Dimensions.dp_16dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        MoviePosterSection(
            movie = movie
        )
        MovieDetailsSection(
            overview = movie.overview ?: ""
        )
    }
}

@Composable
private fun MoviePosterSection(
    movie: MovieUiModel,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        MoviePosterImage(
            posterPath = movie.posterUrl,
            title = movie.title
        )
        MoviePosterOverlay()
        MovieRatingBadge(
            rating = movie.rating ?: 0.0,
            modifier = Modifier.align(Alignment.TopEnd)
        )
        MovieTitleOverlay(
            title = movie.title,
            releaseDate = movie.releaseDate ?: "",
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}

@Composable
private fun MoviePosterImage(
    posterPath: String?,
    title: String,
    modifier: Modifier = Modifier
) {
    NetworkImage(
        imageUrl = if (posterPath != null) "${UiConstants.TMDB_IMAGE_BASE_URL}${posterPath}" else null,
        contentDescription = title,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(AspectRatios.RATIO_4_3)
            .clip(RoundedCornerShape(topStart = Dimensions.dp_16dp, topEnd = Dimensions.dp_16dp)),
        contentScale = ContentScale.Crop,
        shape = RoundedCornerShape(topStart = Dimensions.dp_16dp, topEnd = Dimensions.dp_16dp)
    )
}

@Composable
private fun MoviePosterOverlay(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(AspectRatios.RATIO_4_3)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.7f)
                    ),
                    startY = 0.5f
                )
            )
    )
}

@Composable
private fun MovieRatingBadge(
    rating: Double,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(Dimensions.dp_12dp),
        shape = RoundedCornerShape(Dimensions.dp_20dp),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = Dimensions.dp_8dp,
                vertical = Dimensions.dp_4dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = stringResource(R.string.cd_rating),
                tint = Yellow,
                modifier = Modifier.size(Dimensions.dp_16dp)
            )
            Spacer(modifier = Modifier.width(Dimensions.dp_4dp))
            Text(
                text = stringResource(R.string.movie_rating_format, rating),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun MovieTitleOverlay(
    title: String,
    releaseDate: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(Dimensions.dp_16dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = releaseDate,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

@Composable
private fun MovieDetailsSection(
    overview: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(Dimensions.dp_16dp)
    ) {
        Text(
            text = overview,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieCardPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(Dimensions.dp_16dp),
            verticalArrangement = Arrangement.spacedBy(Dimensions.dp_16dp)
        ) {
            MovieCard(
                movie = MovieUiModel(
                    id = 1,
                    title = stringResource(R.string.sample_movie_title),
                    overview = stringResource(R.string.sample_movie_overview),
                    posterUrl = "/sample_poster.jpg",
                    releaseDate = stringResource(R.string.sample_movie_date),
                    rating = 8.0,
                ),
                onClick = { }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieTitleOverlayPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.dp_100dp)
                .background(Color.Black)
        ) {
            MovieTitleOverlay(
                title = stringResource(R.string.sample_movie_title),
                releaseDate = stringResource(R.string.sample_movie_date),
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }
    }
} 