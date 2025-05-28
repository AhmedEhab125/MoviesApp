package com.example.ui.components.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.constants.UiConstants
import com.example.ui.dimentions.Dimensions

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    message: String = UiConstants.LOADING_MOVIES
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(Dimensions.dp_48dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = Dimensions.dp_4dp
            )
            Spacer(modifier = Modifier.height(Dimensions.dp_16dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingScreenPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(Dimensions.dp_16dp),
            verticalArrangement = Arrangement.spacedBy(Dimensions.dp_16dp)
        ) {
            LoadingScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.dp_200dp),
                message = "Loading movies..."
            )

            LoadingScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.dp_150dp),
                message = "Searching movies..."
            )
        }
    }
} 