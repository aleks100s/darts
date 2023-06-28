import shared

enum GameNavigation {
	case gameFinished
	case showInGameHistory([PlayerHistory], Int32, Int)
	case turnSelected(Turn)
	case replayGame
	case showGameSettings
}
