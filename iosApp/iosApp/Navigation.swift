import shared

internal enum GameNavigation: Hashable {
	case createGame
	case game(players: [Player], goal: Int32)
	case history(game: Game)
}

internal enum StatisticsNavigation {
	
}
