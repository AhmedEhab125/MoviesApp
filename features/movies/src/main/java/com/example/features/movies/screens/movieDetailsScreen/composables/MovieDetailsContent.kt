package com.example.features.movies.screens.movieDetailsScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.ui.model.MovieUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsContent(
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
        MovieDetailsHeader(
            movie = movie,
            onBackClick = onBackClick
        )

        MovieDetailsContentSection(movie = movie)
    }
}


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