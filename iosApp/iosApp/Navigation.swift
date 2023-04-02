import shared

internal enum GameNavigation: Hashable {
	case createGame
	case game(players: [Player], goal: Int32, isFinishWithDoublesChecked: Bool)
	case inGameHistory(gameHistory: [PlayerHistory], goal: Int32, page: Int)
	case history(game: Game)
	case dartsBoard(Set)
	case gameRecap(historyState: HistoryState)
	
	var urlComponent: String? {
		switch self {
		case .createGame:
			return "createGame"
			
		default:
			return nil
		}
	}
}

internal enum StatisticsNavigation: Hashable {
	enum Mode {
		case victoryDistribution
		case shotDistribution
		case heatmapDistribution
		
		var title: String {
			switch self {
			case .shotDistribution:
				return String(localized: "shot_distribution_statistics")
				
			case .victoryDistribution:
				return String(localized: "victory_distribution_statistics")
				
			case .heatmapDistribution:
				return String(localized: "sector_heatmap_statistics")
			}
		}
	}

	case heatmapDistribution(Player)
	case victoryDistribution(Player)
	case shotDistribution(Player)
	case biggestFinalSet
	case bestSet
	case averageValues
	case players(Mode)
	case dartsBoard(Set)
}
