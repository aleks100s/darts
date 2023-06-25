package com.alextos.darts.android.game.create_game

sealed class CreateGameNavigationEvent {
    object CreateGame: CreateGameNavigationEvent()
    object CreatePlayer: CreateGameNavigationEvent()
}
