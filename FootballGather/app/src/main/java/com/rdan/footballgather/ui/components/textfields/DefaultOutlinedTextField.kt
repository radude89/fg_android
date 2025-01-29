package com.rdan.footballgather.ui.components.textfields

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun DefaultOutlinedTextField(
    textValue: String,
    @StringRes labelID: Int,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val fieldColor = MaterialTheme.colorScheme.secondaryContainer
    OutlinedTextField(
        value = textValue,
        onValueChange = onValueChange,
        label = {
            Text(stringResource(labelID))
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = fieldColor,
            unfocusedContainerColor = fieldColor,
            disabledContainerColor = fieldColor,
        ),
        modifier = modifier.fillMaxWidth(),
        singleLine = true
    )
}