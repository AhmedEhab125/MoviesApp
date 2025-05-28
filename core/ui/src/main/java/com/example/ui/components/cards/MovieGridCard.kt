package com.example.ui.components.cards

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
fun MovieGridCard(
    movie: MovieUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(Dimensions.dp_160dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = Dimensions.dp_6dp),
        shape = RoundedCornerShape(Dimensions.dp_12dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            GridCardPosterSection(
                movie = movie
            )
            GridCardInfoSection(
                title = movie.title,
                releaseDate = movie.releaseDate ?: ""
            )
        }
    }
}

@Composable
private fun GridCardPosterSection(
    movie: MovieUiModel,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        GridCardPosterImage(
            posterPath = movie.posterUrl,
            title = movie.title
        )
        CompactRatingBadge(
            rating = movie.rating ?: 0.0,
            modifier = Modifier.align(Alignment.TopEnd)
        )
    }
}

@Composable
private fun GridCardPosterImage(
    posterPath: String?,
    title: String,
    modifier: Modifier = Modifier
) {
    NetworkImage(
        imageUrl = if (posterPath != null) "${UiConstants.TMDB_IMAGE_BASE_URL}${posterPath}" else null,
        contentDescription = title,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(AspectRatios.RATIO_3_4)
            .clip(RoundedCornerShape(topStart = Dimensions.dp_12dp, topEnd = Dimensions.dp_12dp)),
        contentScale = ContentScale.Crop,
        shape = RoundedCornerShape(topStart = Dimensions.dp_12dp, topEnd = Dimensions.dp_12dp)
    )
}

@Composable
private fun CompactRatingBadge(
    rating: Double,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(Dimensions.dp_8dp),
        shape = RoundedCornerShape(Dimensions.dp_16dp),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = Dimensions.dp_6dp,
                vertical = Dimensions.dp_2dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = stringResource(R.string.cd_rating),
                tint = Yellow,
                modifier = Modifier.size(Dimensions.dp_12dp)
            )
            Spacer(modifier = Modifier.width(Dimensions.dp_2dp))
            Text(
                text = stringResource(R.string.movie_rating_format, rating),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun GridCardInfoSection(
    title: String,
    releaseDate: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(Dimensions.dp_12dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            minLines = 2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(Dimensions.dp_4dp))
        Text(
            text = releaseDate,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieGridCardPreview() {
    MaterialTheme {
        Row(
            modifier = Modifier.padding(Dimensions.dp_16dp),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.dp_16dp)
        ) {
            MovieGridCard(
                movie = MovieUiModel(
                    id = 1,
                    title = stringResource(R.string.sample_movie_title),
                    overview = stringResource(R.string.sample_movie_overview_short),
                    posterUrl = "/sample_poster.jpg",
                    releaseDate = stringResource(R.string.sample_movie_date),
                    rating = 8.0,
                ),
                onClick = { }
            )

            MovieGridCard(
                movie = MovieUiModel(
                    id = 2,
                    title = stringResource(R.string.sample_movie_title_2),
                    overview = stringResource(R.string.sample_movie_overview_2),
                    posterUrl = "/sample_poster2.jpg",
                    releaseDate = stringResource(R.string.sample_movie_date_2),
                    rating = 8.4,
                ),
                onClick = { }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CompactRatingBadgePreview() {
    MaterialTheme {
        CompactRatingBadge(
            rating = 8.4
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GridCardInfoSectionPreview() {
    MaterialTheme {
        GridCardInfoSection(
            title = stringResource(R.string.sample_movie_title_2),
            releaseDate = stringResource(R.string.sample_movie_date_2)
        )
    }
} 