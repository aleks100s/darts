import shared

extension GameState {
	var gameFinishedTitle: String {
		guard let result = gameResult else { return "" }
		
		switch result {
		case .TrainingFinished():
			return String(localized: "training_finished")
			
		case .GamePaused():
			return String(localized: "game_progress_saved")
			
		default:
			return String(localized: "game_finished")
		}
	}
	
	var gameFinishedText: String {
		guard let result = gameResult else { return "" }
		
		switch result {
		case .TrainingFinished(), .GamePaused():
			return ""
			
		case .Draw():
			return String(localized: "game_result_is_draw")
			
		default:
			guard let name = result.winnerName else { return "" }
			
			return String(localized: "winner \(name)")
		}
	}
	
	var gameFinishedDismissButtonTitle: String {
		guard let result = gameResult else { return "" }
		
		switch result {
		case .TrainingFinished():
			return String(localized: "leave")
			
		default:
			return String(localized: "finish_game")
		}
	}
}
