package com.example.ui.components.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.R
import com.example.ui.dimentions.Dimensions

@Composable
fun ErrorScreen(
    error: String,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.dp_16dp),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimensions.dp_4dp),
        shape = RoundedCornerShape(Dimensions.dp_16dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.dp_24dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = stringResource(R.string.cd_error),
                tint = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.size(Dimensions.dp_48dp)
            )
            Spacer(modifier = Modifier.height(Dimensions.dp_16dp))
            Text(
                text = stringResource(R.string.error_something_went_wrong),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(Dimensions.dp_8dp))
            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(Dimensions.dp_24dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(R.string.cd_retry),
                    modifier = Modifier.size(Dimensions.dp_18dp)
                )
                Spacer(modifier = Modifier.width(Dimensions.dp_8dp))
                Text(stringResource(R.string.button_retry))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorScreenPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(Dimensions.dp_16dp),
            verticalArrangement = Arrangement.spacedBy(Dimensions.dp_16dp)
        ) {
            ErrorScreen(
                error = stringResource(R.string.error_network_connection),
                onRetry = { }
            )

            ErrorScreen(
                error = stringResource(R.string.error_network_timeout),
                onRetry = { }
            )
        }
    }
} 