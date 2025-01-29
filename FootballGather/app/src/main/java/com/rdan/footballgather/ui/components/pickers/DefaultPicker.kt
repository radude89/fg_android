package com.rdan.footballgather.ui.components.pickers

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.rdan.footballgather.R
import com.rdan.footballgather.ui.components.dropdown.DefaultDropdownMenu
import com.rdan.footballgather.ui.components.textbuttons.PrimaryTextButton

@Composable
fun DefaultPicker(
    @StringRes labelTitleID: Int,
    dropdownTitle: String,
    dropdownEntries: List<String>,
    onDropdownItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            Text(
                text = stringResource(labelTitleID),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            PrimaryTextButton(
                title = dropdownTitle,
                onClick = { expanded = true },
                modifier = modifier
            )
        }
        DefaultDropdownMenu(
            entries = dropdownEntries,
            expanded = expanded,
            onClick = { selectedItem ->
                onDropdownItemSelected(selectedItem)
                expanded = false
            },
            onDismissRequest = { expanded = false }
        )
    }
}