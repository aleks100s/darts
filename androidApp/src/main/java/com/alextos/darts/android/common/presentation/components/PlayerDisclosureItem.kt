package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.core.domain.model.Player

@Composable
fun PlayerDisclosureItem(
    player: Player,
    rightText: String = "",
    showDivider: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp)
            .clickable { onSelect() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PlayerItem(player = player, Modifier.weight(1f))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = rightText)

            Spacer(Modifier.width(16.dp))

            Chevron(contentDescription = stringResource(id = R.string.arrow_right_description))
        }
    }
    if (showDivider) {
        Divider(startIndent = 16.dp)
    }
}