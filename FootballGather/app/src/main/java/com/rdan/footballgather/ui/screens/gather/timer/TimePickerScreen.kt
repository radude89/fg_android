package com.rdan.footballgather.ui.screens.gather.timer

import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.rdan.footballgather.R

@Composable
fun TimePickerScreen(
    selectedMinutes: Int,
    selectedSeconds: Int,
    onMinutesChanged: (Int) -> Unit,
    onSecondsChanged: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.Center
    ) {
        TimePickerContentView(
            selectedMinutes = selectedMinutes,
            selectedSeconds = selectedSeconds,
            onMinutesChanged = onMinutesChanged,
            onSecondsChanged = onSecondsChanged
        )
    }
}

@Composable
private fun TimePickerContentView(
    selectedMinutes: Int,
    selectedSeconds: Int,
    onMinutesChanged: (Int) -> Unit,
    onSecondsChanged: (Int) -> Unit
) {
    NumberPickerColumnView(
        titleID = R.string.minutes,
        range = 0..90,
        selectedValue = selectedMinutes,
        onValueChange = onMinutesChanged
    )
    SpacerView(
        isVertical = false,
        paddingID = R.dimen.padding_medium
    )
    NumberPickerColumnView(
        titleID = R.string.seconds,
        range = 0..59,
        selectedValue = selectedSeconds,
        onValueChange = onSecondsChanged
    )
}

@Composable
private fun NumberPickerColumnView(
    @StringRes titleID: Int,
    range: IntRange,
    selectedValue: Int,
    onValueChange: (Int) -> Unit
) {
    Column {
        Text(
            stringResource(titleID),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        SpacerView()
        NumberPicker(
            range = range,
            selectedValue = selectedValue,
            onValueChange = onValueChange
        )
    }
}

@Composable
private fun SpacerView(
    isVertical: Boolean = true,
    @DimenRes paddingID: Int = R.dimen.padding_small
) {
    val padding = dimensionResource(paddingID)
    Spacer(
        modifier =
        if (isVertical) Modifier.height(padding)
        else Modifier.width(padding)
    )
}

@Composable
fun NumberPicker(
    range: IntRange,
    selectedValue: Int,
    onValueChange: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .width(dimensionResource(R.dimen.number_picker_width))
            .height(dimensionResource(R.dimen.number_picker_height))
    ) {
        NumberPickerListContent(
            range = range,
            selectedValue = selectedValue,
            onValueChange = onValueChange
        )
    }
}

@Composable
private fun NumberPickerListContent(
    range: IntRange,
    selectedValue: Int,
    onValueChange: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        items(range.toList()) { value ->
            NumberPickerItemText(
                value = value,
                selectedValue = selectedValue,
                onValueChange = onValueChange
            )
        }
    }
}

@Composable
private fun NumberPickerItemText(
    value: Int,
    selectedValue: Int,
    onValueChange: (Int) -> Unit
) {
    Text(
        text = value.toString().padStart(2, '0'),
        style =
            if (value == selectedValue) MaterialTheme.typography.bodyLarge
            else MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .clickable { onValueChange(value) },
        )
}