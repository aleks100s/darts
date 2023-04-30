package com.alextos.darts.android.common.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R

@Composable
fun Screen(
    title: String,
    isBackButtonVisible: Boolean = true,
    backIcon: ImageVector = Icons.Filled.ArrowBack,
    onBackPressed: () -> Unit,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar {
                NavigationBar(
                    title,
                    isBackButtonVisible,
                    backIcon,
                    onBackPressed
                )
            }
        }
    ) {
        content(Modifier.padding(it))
    }
}

@Composable
private fun NavigationBar(
    title: String,
    isBackButtonVisible: Boolean,
    backIcon: ImageVector,
    onBackPressed: () -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isBackButtonVisible) {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = backIcon,
                    contentDescription = stringResource(id = R.string.navigate_back)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.h2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}