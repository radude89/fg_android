package com.rdan.footballgather.ui.components.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.rdan.footballgather.R

@Composable
fun DefaultDropdownMenu(
    entries: List<String>,
    expanded: Boolean,
    onClick: (String) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = dimensionResource(R.dimen.padding_small))
    ) {
        entries.forEachIndexed { index, entry ->
            DefaultDropdownMenuItem(
                title = entry,
                onClick = { onClick(entry) },
            )
            if (index < entries.size - 1) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme
                        .onPrimaryContainer.copy(alpha = 0.2f),
                    modifier = Modifier
                        .padding(start = dimensionResource(R.dimen.padding_small))
                )
            }
        }
    }
}