package com.alextos.darts.game.domain

import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.domain.models.GameHistory
import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.core.domain.Player
import kotlinx.coroutines.flow.Flow

interface GameDataSource {
    fun getGames(): Flow<List<Game>>
    fun saveGameHistory(gameHistory: GameHistory)
    fun getPlayerHistory(gameId: Long, player: Player): PlayerHistory
    fun deleteGame(game: Game)
}