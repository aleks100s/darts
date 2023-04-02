package com.alextos.darts.android.common.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.core.domain.model.Sector

@Composable
fun Sector.inputString(): String {
    return when (this) {
        Sector.Miss -> stringResource(id = R.string.miss)
        else -> valueString()
    }
}