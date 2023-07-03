import shared

enum GameListNavigation {
	case createGame
	case gameSelected(Game)
	case replay(Game)
	case showCalculator
	case resumeGame(Game)
}
