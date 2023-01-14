package com.alextos.darts.game.domain.useCases

import com.alextos.darts.players.domain.models.Player
import com.alextos.darts.game.domain.GameDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class GameManager(
    private val gameDataSource: GameDataSource,
    private val players: List<Player>
) {
    private val _currentPlayer = MutableStateFlow(players[0])
    private val _isGameFinished = MutableStateFlow(false)

    init {

    }

    private fun nextTurn() {
        when (players.count()) {
            1 -> {
                _currentPlayer.update { players[0] }
            }
            2 -> {
                when (_currentPlayer.value) {
                    players[0] -> _currentPlayer.update { players[1] }
                    players[1] -> _currentPlayer.update { players[0] }
                }
            }
            3 -> {
                when (_currentPlayer.value) {
                    players[0] -> _currentPlayer.update { players[1] }
                    players[1] -> _currentPlayer.update { players[2] }
                    players[2] -> _currentPlayer.update { players[0] }
                }
            }
            4 -> {
                when (_currentPlayer.value) {
                    players[0] -> _currentPlayer.update { players[1] }
                    players[1] -> _currentPlayer.update { players[2] }
                    players[2] -> _currentPlayer.update { players[3] }
                    players[3] -> _currentPlayer.update { players[0] }
                }
            }
            else -> {}
        }
    }
}