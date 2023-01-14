package com.alextos.darts.game.domain

import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.domain.models.GameHistory
import kotlinx.coroutines.flow.Flow

interface GameDataSource {
    fun getGames(): Flow<List<Game>>
    fun saveGameHistory(gameHistory: GameHistory)
    fun getGameHistory(game: Game): GameHistory
}