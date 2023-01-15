package com.alextos.darts.game.domain

import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.domain.models.GameHistory
import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.players.domain.models.Player
import kotlinx.coroutines.flow.Flow

interface GameDataSource {
    fun getGames(): Flow<List<Game>>
    fun saveGameHistory(gameHistory: GameHistory)
    fun getGameHistory(gameId: Long, players: List<Player>): List<PlayerHistory>
}