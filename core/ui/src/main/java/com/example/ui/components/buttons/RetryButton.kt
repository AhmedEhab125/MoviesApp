package com.example.ui.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.R
import com.example.ui.dimentions.Dimensions

@Composable
fun RetryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.button_retry)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = stringResource(R.string.cd_retry)
        )
        Spacer(modifier = Modifier.width(Dimensions.dp_8dp))
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
private fun RetryButtonPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(Dimensions.dp_16dp),
            verticalArrangement = Arrangement.spacedBy(Dimensions.dp_8dp)
        ) {
            RetryButton(
                onClick = { }
            )

            RetryButton(
                onClick = { },
                text = "try again"
            )
        }
    }
} 