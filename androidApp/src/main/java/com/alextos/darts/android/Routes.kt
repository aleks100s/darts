package com.alextos.darts.android

sealed class Route(val route: String) {
    object GameList: Route("game_list")
    object CreateGame: Route("create_game")
    object Game: Route("game")

    fun routeWithArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { append("/$it") }
        }
    }
}