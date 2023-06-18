package com.alextos.darts.android.di

import android.app.Application
import com.alextos.darts.core.data.DatabaseDriverFactory
import com.alextos.darts.core.data.SqlDelightPlayersDataSource
import com.alextos.darts.core.domain.useCases.DeletePlayerUseCase
import com.alextos.darts.core.domain.PlayersDataSource
import com.alextos.darts.database.DartsDatabase
import com.alextos.darts.game.data.SqlDelightGameDataSource
import com.alextos.darts.game.domain.GameDataSource
import com.alextos.darts.core.domain.useCases.GetPlayersUseCase
import com.alextos.darts.debug.PrepopulateDatabaseUseCase
import com.alextos.darts.game.domain.useCases.*
import com.alextos.darts.statistics.data.SqlDelightStatisticsDataSource
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.use_cases.average_values.GetOverallAverageTurnScoreUseCase
import com.alextos.darts.statistics.domain.use_cases.average_values.GetOverallAverageShotValueUseCase
import com.alextos.darts.statistics.domain.use_cases.average_values.GetPlayersAverageValuesUseCase
import com.alextos.darts.statistics.domain.use_cases.best_turn.GetPlayersBestTurnsUseCase
import com.alextos.darts.statistics.domain.use_cases.biggest_final_turn.GetPlayersBiggestFinalTurnUseCase
import com.alextos.darts.statistics.domain.use_cases.heatmap.GetSectorHeatmapUseCase
import com.alextos.darts.statistics.domain.use_cases.shot_distribution.GetPlayerShotDistributionUseCase
import com.alextos.darts.statistics.domain.use_cases.time.GetGlobalTotalTimePlayedUseCase
import com.alextos.darts.statistics.domain.use_cases.time.GetPlayersTotalTimePlayed
import com.alextos.darts.statistics.domain.use_cases.time.TimeDurationConverter
import com.alextos.darts.statistics.domain.use_cases.victory_distribution.GetPlayerVictoryDistributionUseCase
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
    fun providePlayersDataSource(dartsDatabase: DartsDatabase): PlayersDataSource {
        return SqlDelightPlayersDataSource(dartsDatabase)
    }

    @Provides
    @Singleton
    fun provideGetPlayersUseCase(dataSource: PlayersDataSource): GetPlayersUseCase {
        return GetPlayersUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideCreatePlayerUseCase(dataSource: PlayersDataSource): CreatePlayerUseCase {
       return CreatePlayerUseCase(dataSource)
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
    fun provideStatisticsDataSource(database: DartsDatabase): StatisticsDataSource {
        return SqlDelightStatisticsDataSource(database)
    }

    @Provides
    @Singleton
    fun provideGetPlayersBestSetsUseCase(dataSource: StatisticsDataSource): GetPlayersBestTurnsUseCase {
        return GetPlayersBestTurnsUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetPlayerBiggestFinalSetUseCase(dataSource: StatisticsDataSource): GetPlayersBiggestFinalTurnUseCase {
        return GetPlayersBiggestFinalTurnUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetAverageSetScoreUseCase(dataSource: StatisticsDataSource): GetOverallAverageTurnScoreUseCase {
        return GetOverallAverageTurnScoreUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetAverageShotValueUseCase(dataSource: StatisticsDataSource): GetOverallAverageShotValueUseCase {
        return GetOverallAverageShotValueUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetPlayersAverageValuesUseCase(dataSource: StatisticsDataSource): GetPlayersAverageValuesUseCase {
        return GetPlayersAverageValuesUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetPlayerShotDistributionUseCase(dataSource: StatisticsDataSource): GetPlayerShotDistributionUseCase {
        return GetPlayerShotDistributionUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetPlayerVictoryDistributionUseCase(dataSource: StatisticsDataSource): GetPlayerVictoryDistributionUseCase {
        return GetPlayerVictoryDistributionUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetSectorHeatmapDistributionUseCase(dataSource: StatisticsDataSource): GetSectorHeatmapUseCase {
        return GetSectorHeatmapUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideDeleteGameUseCase(dataSource: GameDataSource): DeleteGameUseCase {
        return DeleteGameUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideDeletePlayerUseCase(dataSource: PlayersDataSource): DeletePlayerUseCase {
        return DeletePlayerUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetPlayerAverageTurnUseCase(dataSource: StatisticsDataSource): GetPlayerAverageTurnUseCase {
        return GetPlayerAverageTurnUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun providePrepopulateDatabaseUseCase(
        createPlayerUseCase: CreatePlayerUseCase,
        saveGameHistoryUseCase: SaveGameHistoryUseCase
    ): PrepopulateDatabaseUseCase {
        return PrepopulateDatabaseUseCase(createPlayerUseCase, saveGameHistoryUseCase)
    }

    @Provides
    @Singleton
    fun provideTimeDurationConverter(): TimeDurationConverter {
        return TimeDurationConverter()
    }

    @Provides
    @Singleton
    fun provideGetGlobalTotalTimePlayedUseCase(
        dataSource: StatisticsDataSource,
        converter: TimeDurationConverter
    ): GetGlobalTotalTimePlayedUseCase {
        return GetGlobalTotalTimePlayedUseCase(dataSource, converter)
    }

    @Provides
    @Singleton
    fun provideGetPlayersTotalTimePlayedUseCase(
        dataSource: StatisticsDataSource,
        converter: TimeDurationConverter
    ): GetPlayersTotalTimePlayed {
        return GetPlayersTotalTimePlayed(dataSource, converter)
    }
}