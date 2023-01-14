package com.alextos.darts.core.data

import android.content.Context
import com.alextos.darts.database.DartsDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver = AndroidSqliteDriver(
        schema = DartsDatabase.Schema,
        context = context,
        name = "darts.db"
    )
}