import shared

internal enum GameNavigation: Hashable {
	case createGame
	case game(settings: GameSettings)
	case inGameHistory(gameHistory: [PlayerHistory], goal: Int32, page: Int)
	case history(game: Game)
	case dartsBoard(Turn)
	case gameRecap(historyState: HistoryState)
	case calculator
	case calculatorHistory(turns: [Turn])
	
	var urlComponent: String? {
		switch self {
		case .createGame:
			return "createGame"
			
		case .calculator:
			return "calculator"
			
		default:
			return nil
		}
	}
}

extension [GameNavigation] {
	var isGameRunning: Bool {
		for element in self {
			switch element {
			case .game:
				return true
				
			default:
				continue
			}
		}
		return false
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
	case biggestFinalTurn
	case bestTurn
	case averageValues
	case time
	case players(Mode)
	case dartsBoard(Turn)
}
