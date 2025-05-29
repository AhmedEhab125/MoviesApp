package com.example.features.movies.screens.movieDetailsScreen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.base.SideEffectsKey
import com.example.ui.R
import com.example.ui.components.images.NetworkImage
import com.example.ui.components.loading.LoadingScreen
import com.example.ui.components.states.ErrorScreen
import com.example.ui.constants.UiConstants
import com.example.ui.dimentions.Dimensions
import com.example.ui.model.MovieUiModel
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    state: MovieDetailsState,
    onEvent: (MovieDetailsEvent) -> Unit,
    effect: Flow<MovieDetailsSideEffect>,
    onNavigationRequested: (MovieDetailsSideEffect.Navigation) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(SideEffectsKey) {
        effect.collect {
            when (it) {
                is MovieDetailsSideEffect.Navigation.NavigateBack -> {
                    onNavigationRequested(it)
                }
            }
        }
    }

    when (state) {
        is MovieDetailsState.Idle -> {
            // This will be handled by the route
        }

        is MovieDetailsState.Loading -> {
            LoadingScreen(message = stringResource(R.string.loading_movie_details))
        }

        is MovieDetailsState.Error -> {
            ErrorScreen(
                error = state.message,
                onRetry = { /* Will be handled by navigation */ }
            )
        }

        is MovieDetailsState.DataLoaded -> {
            MovieDetailsContent(
                movie = state.movie,
                onBackClick = { onEvent(MovieDetailsEvent.NavigateTo(MovieDetailsSideEffect.Navigation.NavigateBack)) },
                modifier = modifier
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetailsContent(
    movie: MovieUiModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // Header with backdrop image and back button
        MovieDetailsHeader(
            movie = movie,
            onBackClick = onBackClick
        )

        // Movie details content
        MovieDetailsContentSection(movie = movie)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetailsHeader(
    movie: MovieUiModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimensions.dp_400dp)
    ) {
        // Backdrop image
        NetworkImage(
            imageUrl = "${UiConstants.TMDB_IMAGE_BASE_URL_W780}${movie.posterUrl}",
            contentDescription = movie.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.1f),
                            Color.Black.copy(alpha = 0.4f),
                            Color.Black.copy(alpha = 0.8f)
                        ),
                        startY = 0.2f
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
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                            RoundedCornerShape(Dimensions.dp_50dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
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
            text = movie.title,
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
                MovieInfoChip(
                    icon = Icons.Default.Star,
                    text = String.format("%.1f", rating),
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.95f),
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    iconTint = Color(0xFFFFD700)
                )
            }

            // Release date
            movie.releaseDate?.let { releaseDate ->
                MovieInfoChip(
                    icon = Icons.Default.DateRange,
                    text = releaseDate,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f),
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
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
                contentDescription = null,
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

@Composable
private fun MovieDetailsContentSection(
    movie: MovieUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.dp_16dp),
        verticalArrangement = Arrangement.spacedBy(Dimensions.dp_20dp)
    ) {
        // Movie quick stats
        MovieQuickStatsCard(movie = movie)

        // Overview section
        movie.overview?.let { overview ->
            MovieOverviewCard(overview = overview)
        }

        // Additional spacing at bottom
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
                text = "Movie Information",
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
                    MovieStatItem(
                        icon = Icons.Default.Star,
                        label = "Rating",
                        value = "${String.format("%.1f", rating)}/10",
                        iconTint = Color(0xFFFFD700)
                    )
                }

                movie.releaseDate?.let { releaseDate ->
                    MovieStatItem(
                        icon = Icons.Default.DateRange,
                        label = "Release Date",
                        value = releaseDate,
                        iconTint = MaterialTheme.colorScheme.primary
                    )
                }

                // Movie quality indicator
                movie.rating?.let { rating ->
                    val quality = when {
                        rating >= 8.0 -> "Excellent"
                        rating >= 7.0 -> "Very Good"
                        rating >= 6.0 -> "Good"
                        rating >= 5.0 -> "Average"
                        else -> "Below Average"
                    }

                    MovieStatItem(
                        icon = Icons.Default.ThumbUp,
                        label = "Quality",
                        value = quality,
                        iconTint = when {
                            rating >= 8.0 -> Color(0xFF4CAF50)
                            rating >= 7.0 -> Color(0xFF8BC34A)
                            rating >= 6.0 -> Color(0xFFFFEB3B)
                            rating >= 5.0 -> Color(0xFFFF9800)
                            else -> Color(0xFFF44336)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieStatItem(
    icon: ImageVector,
    label: String,
    value: String,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                contentDescription = null,
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
                text = "Synopsis",
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
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight.times(1.2f)
            )
        }
    }
}

// Preview data provider
class MovieDetailsPreviewParameterProvider : PreviewParameterProvider<MovieUiModel> {
    override val values = sequenceOf(
        MovieUiModel(
            id = 1,
            title = "The Avengers",
            overview = "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity. This epic superhero ensemble brings together Iron Man, Captain America, Thor, Hulk, Black Widow, and Hawkeye for an unforgettable adventure.",
            posterUrl = "/sample_poster.jpg",
            releaseDate = "2012-04-25",
            rating = 8.0
        ),
        MovieUiModel(
            id = 2,
            title = "Spider-Man: No Way Home",
            overview = "Peter Parker's secret identity is revealed to the entire world. Desperate for help, Peter turns to Doctor Strange to make the world forget that he is Spider-Man.",
            posterUrl = "/sample_poster2.jpg",
            releaseDate = "2021-12-15",
            rating = 8.4
        )
    )
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun MovieDetailsContentPreview(
    @PreviewParameter(MovieDetailsPreviewParameterProvider::class) movie: MovieUiModel
) {
    MaterialTheme {
        MovieDetailsContent(
            movie = movie,
            onBackClick = { }
        )
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun MovieDetailsContentDarkPreview(
    @PreviewParameter(MovieDetailsPreviewParameterProvider::class) movie: MovieUiModel
) {
    MaterialTheme {
        MovieDetailsContent(
            movie = movie,
            onBackClick = { }
        )
    }
} 