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
    additionalNavBarContent: @Composable (() -> Unit)? = null,
    content: @Composable (Modifier) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar {
                NavigationBar(
                    title,
                    isBackButtonVisible,
                    backIcon,
                    onBackPressed,
                    additionalNavBarContent
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
    onBackPressed: () -> Unit,
    additionalContent: @Composable (() -> Unit)?
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
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
                text = title,
                style = MaterialTheme.typography.h2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        additionalContent?.invoke()
    }
}