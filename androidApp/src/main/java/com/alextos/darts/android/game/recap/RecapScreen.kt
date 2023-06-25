package com.alextos.darts.android.game.recap

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.FileProvider
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.GameRecapView
import com.alextos.darts.game.presentation.history.HistoryState
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@Composable
fun RecapScreen(
    historyState: HistoryState,
    onBackPressed: () -> Unit
) {
    val captureController = rememberCaptureController()
    val context = LocalContext.current

    Screen(
        title = stringResource(id = R.string.game_recap),
        onBackPressed = onBackPressed,
        additionalNavBarContent = {
            IconButton(onClick = { captureController.capture() }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = stringResource(id = R.string.share)
                )
            }
        }
    ) {
        Capturable(
            controller = captureController,
            onCaptured = { bitmap, error ->
                // This is captured bitmap of a content inside Capturable Composable.
                if (bitmap != null) {
                    saveImage(context, bitmap.asAndroidBitmap())
                    shareImage(context)
                    // Bitmap is captured successfully. Do something with it!
                }

                if (error != null) {
                    Log.e(null, error.message ?: "", error)
                }
            }
        ) {
            GameRecapView(
                history = historyState.gameHistory,
                averageTurns = historyState.averageTurns(),
                biggestTurns = historyState.biggestTurns(),
                smallestTurns = historyState.smallestTurns(),
                misses = historyState.misses(),
                overkills = historyState.overkills(),
                duration = historyState.duration,
                numberOfTurns = historyState.numberOfTurns
            )
        }
    }
}

private fun saveImage(context: Context, bitmap: Bitmap) {
    try {
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs() // don't forget to make the directory
        val stream = FileOutputStream("$cachePath/image.png") // overwrites this image every time
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

private fun shareImage(context: Context) {
    val imagePath = File(context.cacheDir, "images")
    val newFile = File(imagePath, "image.png")
    val contentUri = FileProvider.getUriForFile(context, "com.alextos.darts.android", newFile)

    val shareIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // temp permission for receiving app to read this file
        // Example: content://com.google.android.apps.photos.contentprovider/...
        putExtra(Intent.EXTRA_STREAM, contentUri)
        type = "image/jpeg"
    }
    context.startActivity(Intent.createChooser(shareIntent, null))
}