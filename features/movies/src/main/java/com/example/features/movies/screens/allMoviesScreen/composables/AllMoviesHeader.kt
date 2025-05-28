package com.example.features.movies.screens.allMoviesScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.features.movies.screens.allMoviesScreen.MoviesState
import com.example.features.movies.screens.allMoviesScreen.ViewMode
import com.example.ui.R
import com.example.ui.dimentions.Dimensions


@Composable
fun AllMoviesHeader(
    state: MoviesState.DataLoaded,
    modifier: Modifier = Modifier,
    onChangeModeClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Dimensions.dp_16dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (state.searchQuery.isNotEmpty())
                stringResource(R.string.search_results_title)
            else
                stringResource(R.string.popular_movies_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        IconButton(
            onClick = onChangeModeClicked
        ) {
            Icon(
                imageVector = if (state.viewMode == ViewMode.LIST) Icons.Default.Menu else Icons.Default.List,
                contentDescription = stringResource(R.string.cd_toggle_view_mode),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }

}