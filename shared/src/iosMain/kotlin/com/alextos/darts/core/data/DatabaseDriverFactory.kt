package com.alextos.darts.core.data

import com.alextos.darts.database.DartsDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver = NativeSqliteDriver(
        schema = DartsDatabase.Schema,
        name = "darts.db"
    )
}