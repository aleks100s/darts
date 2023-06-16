package com.alextos.darts.android.navigation.game

sealed class GameRoute(val route: String) {
    object GameList: GameRoute("game_list")
    object CreateGame: GameRoute("create_game")
    object Game: GameRoute("game")
    object History: GameRoute("history")
    object Darts: GameRoute("darts")
    object InGameHistory: GameRoute("in_game_history")
    object Recap: GameRoute("recap")
    object Calculator: GameRoute("calculator")
    object CalculatorHistory: GameRoute("calculator_history")

    fun routeWithArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { append("/$it") }
        }
    }
}