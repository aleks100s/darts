package com.alextos.darts.android.di

import android.app.Application
import com.alextos.darts.core.data.DatabaseDriverFactory
import com.alextos.darts.database.DartsDatabase
import com.alextos.darts.game.data.SqlDelightGameDataSource
import com.alextos.darts.game.domain.GameDataSource
import com.alextos.darts.game.domain.useCases.GameManager
import com.alextos.darts.game.domain.useCases.GetGameHistoryUseCase
import com.alextos.darts.game.domain.useCases.GetGamesUseCase
import com.alextos.darts.game.domain.useCases.SaveGameHistoryUseCase
import com.alextos.darts.players.data.SqlDelightPlayerDataSource
import com.alextos.darts.players.domain.PlayerDataSource
import com.alextos.darts.players.domain.models.Player
import com.alextos.darts.players.domain.useCases.CreatePlayerUseCase
import com.alextos.darts.players.domain.useCases.GetPlayersUseCase
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabaseDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun provideDatabase(driver: SqlDriver): DartsDatabase {
        return DartsDatabase(driver)
    }

    @Provides
    @Singleton
    fun providesPlayerDataSource(database: DartsDatabase): PlayerDataSource {
        return SqlDelightPlayerDataSource(database)
    }

    @Provides
    @Singleton
    fun provideCreatePlayerUseCase(dataSource: PlayerDataSource): CreatePlayerUseCase {
       return CreatePlayerUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetPlayersUseCase(dataSource: PlayerDataSource): GetPlayersUseCase {
        return GetPlayersUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideGameDataSource(database: DartsDatabase): GameDataSource {
        return SqlDelightGameDataSource(database)
    }

    @Provides
    @Singleton
    fun provideGetGamesUseCase(dataSource: GameDataSource): GetGamesUseCase {
        return GetGamesUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideSaveGameHistoryUseCase(dataSource: GameDataSource): SaveGameHistoryUseCase {
        return SaveGameHistoryUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetGameHistoryUseCase(dataSource: GameDataSource): GetGameHistoryUseCase {
        return GetGameHistoryUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideGameManager(dataSource: GameDataSource): GameManager {
        return GameManager(dataSource, listOf(Player(name = "Kek lol"), Player(name = "Lol kek")))
    }
}