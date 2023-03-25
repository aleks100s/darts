package com.alextos.darts.android.di

import android.app.Application
import com.alextos.darts.core.data.DatabaseDriverFactory
import com.alextos.darts.core.data.SqlDelightPlayersDataSource
import com.alextos.darts.core.domain.DeletePlayerUseCase
import com.alextos.darts.core.domain.PlayersDataSource
import com.alextos.darts.database.DartsDatabase
import com.alextos.darts.game.data.SqlDelightGameDataSource
import com.alextos.darts.game.domain.GameDataSource
import com.alextos.darts.core.domain.GetPlayersUseCase
import com.alextos.darts.game.domain.useCases.*
import com.alextos.darts.statistics.data.SqlDelightStatisticsDataSource
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.use_cases.average_values.GetAverageSetScoreUseCase
import com.alextos.darts.statistics.domain.use_cases.average_values.GetAverageShotValueUseCase
import com.alextos.darts.statistics.domain.use_cases.average_values.GetPlayersAverageValuesUseCase
import com.alextos.darts.statistics.domain.use_cases.best_set.GetPlayersBestSetsUseCase
import com.alextos.darts.statistics.domain.use_cases.biggest_final_set.GetPlayersBiggestFinalSetUseCase
import com.alextos.darts.statistics.domain.use_cases.heatmap.GetSectorHeatmapUseCase
import com.alextos.darts.statistics.domain.use_cases.shot_distribution.GetPlayerShotDistributionUseCase
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
    fun provideGetPlayersBestSetsUseCase(dataSource: StatisticsDataSource): GetPlayersBestSetsUseCase {
        return GetPlayersBestSetsUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetPlayerBiggestFinalSetUseCase(dataSource: StatisticsDataSource): GetPlayersBiggestFinalSetUseCase {
        return GetPlayersBiggestFinalSetUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetAverageSetScoreUseCase(dataSource: StatisticsDataSource): GetAverageSetScoreUseCase {
        return GetAverageSetScoreUseCase(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetAverageShotValueUseCase(dataSource: StatisticsDataSource): GetAverageShotValueUseCase {
        return GetAverageShotValueUseCase(dataSource)
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
}