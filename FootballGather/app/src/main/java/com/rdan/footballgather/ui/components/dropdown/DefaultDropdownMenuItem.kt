package com.rdan.footballgather.ui.components.dropdown

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.rdan.footballgather.R

@Composable
fun DefaultDropdownMenuItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenuItem(
        onClick = onClick,
        text = {
            Text(
                text = title.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        modifier = modifier
            .padding(vertical = dimensionResource(R.dimen.padding_verySmall))
    )
}