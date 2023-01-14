package com.alextos.darts.game.domain

import com.alextos.darts.core.util.CommonFlow
import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.domain.models.GameHistory

interface GameDataSource {
    fun getGames(): CommonFlow<List<Game>>
    fun saveGameHistory(gameHistory: GameHistory)
    fun getGameHistory(game: Game): GameHistory
}