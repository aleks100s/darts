package com.alextos.darts.di

import com.alextos.darts.core.data.DatabaseDriverFactory
import com.alextos.darts.core.data.SqlDelightPlayersDataSource
import com.alextos.darts.core.domain.PlayersDataSource
import com.alextos.darts.database.DartsDatabase
import com.alextos.darts.game.data.SqlDelightGameDataSource
import com.alextos.darts.game.domain.GameDataSource
import com.alextos.darts.game.domain.useCases.GetGamesUseCase

class AppModule {
    val gameDataSource: GameDataSource by lazy {
        SqlDelightGameDataSource(database = database)
    }

    val playerDataSource: PlayersDataSource by lazy {
        SqlDelightPlayersDataSource(database = database)
    }

    val getGamesUseCase: GetGamesUseCase by lazy {
        GetGamesUseCase(dataSource = gameDataSource)
    }

    private val database: DartsDatabase by lazy {
        DartsDatabase(
            DatabaseDriverFactory().create()
        )
    }
}