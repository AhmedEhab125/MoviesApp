package com.example.ui.components.images

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ui.R
import com.example.ui.dimentions.Dimensions

@Composable
fun NetworkImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    shape: Shape? = null,
    contentScale: ContentScale = ContentScale.Crop,
    placeholder: Painter? = null,
    error: Painter? = null
) {
    val imageModifier = if (shape != null) {
        modifier.clip(shape)
    } else {
        modifier
    }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        modifier = imageModifier,
        contentScale = contentScale,
        placeholder = placeholder,
        error = error
    )
}

@Preview(showBackground = true)
@Composable
private fun NetworkImagePreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(Dimensions.dp_16dp),
            verticalArrangement = Arrangement.spacedBy(Dimensions.dp_16dp)
        ) {
            // Valid image URL (placeholder will show while loading)
            NetworkImage(
                imageUrl = "https://image.tmdb.org/t/p/w500/sample.jpg",
                contentDescription = stringResource(R.string.sample_movie_title),
                modifier = Modifier
                    .size(Dimensions.dp_100dp),
                shape = RoundedCornerShape(Dimensions.dp_8dp)
            )

            // Invalid image URL (error view will show)
            NetworkImage(
                imageUrl = "invalid_url",
                contentDescription = stringResource(R.string.cd_no_poster),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.dp_100dp),
                shape = RoundedCornerShape(Dimensions.dp_12dp)
            )

            // Null image URL (error view will show)
            NetworkImage(
                imageUrl = null,
                contentDescription = stringResource(R.string.cd_no_poster),
                modifier = Modifier
                    .size(Dimensions.dp_100dp),
                shape = RoundedCornerShape(Dimensions.dp_8dp)
            )
        }
    }
} 