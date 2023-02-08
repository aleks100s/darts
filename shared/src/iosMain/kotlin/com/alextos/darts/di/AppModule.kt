package com.alextos.darts.di

import com.alextos.darts.core.data.DatabaseDriverFactory
import com.alextos.darts.database.DartsDatabase
import com.alextos.darts.game.data.SqlDelightGameDataSource
import com.alextos.darts.game.domain.GameDataSource
import com.alextos.darts.game.domain.useCases.GetGamesUseCase

class AppModule {
    val gameDataSource: GameDataSource by lazy {
        SqlDelightGameDataSource(
            database = DartsDatabase(
                DatabaseDriverFactory().create()
            )
        )
    }

    val getGamesUseCase: GetGamesUseCase by lazy {
        GetGamesUseCase(dataSource = gameDataSource)
    }
}