package com.example.ui.components.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.R
import com.example.ui.dimentions.Dimensions

@Composable
fun SecondaryButton(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    @StringRes iconContentDescriptionRes: Int? = null,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = iconContentDescriptionRes?.let { stringResource(it) },
                modifier = Modifier.size(Dimensions.dp_18dp)
            )
            Spacer(modifier = Modifier.width(Dimensions.dp_8dp))
        }
        Text(stringResource(textRes))
    }
}

@Preview(showBackground = true)
@Composable
private fun SecondaryButtonPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(Dimensions.dp_16dp),
            verticalArrangement = Arrangement.spacedBy(Dimensions.dp_8dp)
        ) {
            SecondaryButton(
                textRes = R.string.button_retry,
                onClick = { }
            )

            SecondaryButton(
                textRes = R.string.button_retry,
                onClick = { },
                icon = Icons.Default.Refresh,
                iconContentDescriptionRes = R.string.cd_retry
            )

            SecondaryButton(
                textRes = R.string.button_retry,
                onClick = { },
                enabled = false
            )
        }
    }
} 