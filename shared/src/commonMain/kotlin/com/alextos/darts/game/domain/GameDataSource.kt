package com.alextos.darts.game.domain

import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.domain.models.GameHistory
import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.core.domain.model.Player
import kotlinx.coroutines.flow.Flow

interface GameDataSource {
    fun getGames(): Flow<List<Game>>
    suspend fun saveGameHistory(gameHistory: GameHistory)
    fun getPlayerHistory(gameId: Long, player: Player): Flow<PlayerHistory>
    suspend fun getPlayerHistoryOnce(gameId: Long, player: Player): PlayerHistory
    suspend fun deleteGame(game: Game)
}