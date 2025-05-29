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
import com.example.ui.constants.UiColors
import com.example.ui.constants.UiConstants
import com.example.ui.dimentions.Dimensions
import com.example.ui.model.MovieUiModel
import com.example.ui.utils.MovieUtils
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
            imageUrl = MovieUtils.getFullPosterUrl(movie.posterUrl),
            contentDescription = MovieUtils.getSafeMovieTitle(movie),
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
                        contentDescription = stringResource(R.string.cd_back_button),
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