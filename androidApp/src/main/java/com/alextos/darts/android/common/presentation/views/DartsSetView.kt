package com.alextos.darts.android.common.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.common.presentation.components.DartsDisk
import com.alextos.darts.android.common.presentation.extensions.inputString
import com.alextos.darts.core.domain.Shot

@Composable
fun DartsSetView(set: List<Shot>) {
    LazyColumn {
        items(set) {
            DartsShotView(shot = it)
        }
    }
}

@Composable
private fun DartsShotView(shot: Shot) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(text = shot.sector.inputString())
        Spacer(Modifier.height(16.dp))
        DartsDisk(selectedSector = shot.sector)
    }
}