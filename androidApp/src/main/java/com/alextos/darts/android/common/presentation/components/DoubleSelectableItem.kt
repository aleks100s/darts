package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R

@Composable
fun DoubleSelectableItem(
    leftText: String,
    rightText: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = leftText)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = rightText)
            Spacer(Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.ArrowRight,
                contentDescription = stringResource(id = R.string.arrow_right_description)
            )
        }
    }
}