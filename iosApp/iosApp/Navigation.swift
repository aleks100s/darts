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
	enum Mode {
		case victoryDistribution
		case shotDistribution
		
		var title: String {
			switch self {
			case .shotDistribution:
				return String(localized: "shot_distribution_statistics")
				
			case .victoryDistribution:
				return String(localized: "victory_distribution_statistics")
			}
		}
	}

	case victoryDistribution(Player)
	case shotDistribution(Player)
	case biggestFinalSet
	case mostFrequentShots
	case bestSet
	case averageValues
	case players(Mode)
	case dartsBoard(Set)
}
