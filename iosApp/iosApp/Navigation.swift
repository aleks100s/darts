import shared

internal enum GameNavigation: Hashable {
	case createGame
	case game(players: [Player], goal: Int32)
	case inGameHistory(gameHistory: [PlayerHistory], goal: Int32)
	case history(game: Game)
	case dartsBoard(Set)
	case gameRecap(historyState: HistoryState)
}

internal enum StatisticsNavigation: Hashable {
	case victoryDistribution
	case shotDistribution
	case biggestFinalSet
	case mostFrequentShots
	case bestSet
	case averageValues
	case dartsBoard(Set)
}
