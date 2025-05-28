package com.example.ui.components.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.R
import com.example.ui.dimentions.Dimensions

@Composable
fun PrimaryButton(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    @StringRes iconContentDescriptionRes: Int? = null,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = iconContentDescriptionRes?.let { stringResource(it) }
            )
            Spacer(modifier = Modifier.width(Dimensions.dp_8dp))
        }
        Text(stringResource(textRes))
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaryButtonPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(Dimensions.dp_16dp),
            verticalArrangement = Arrangement.spacedBy(Dimensions.dp_8dp)
        ) {
            PrimaryButton(
                textRes = R.string.button_retry,
                onClick = { }
            )

            PrimaryButton(
                textRes = R.string.button_retry,
                onClick = { },
                icon = Icons.Default.PlayArrow,
            )

            PrimaryButton(
                textRes = R.string.button_retry,
                onClick = { },
                enabled = false
            )
        }
    }
} 