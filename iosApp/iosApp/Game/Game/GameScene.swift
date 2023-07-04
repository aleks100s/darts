import SwiftUI
import shared

enum GameScene {
	static func create(
		using module: AppModule,
		gameSettings: GameSettings,
		pausedGame: Game?,
		onNavigation: @escaping (GameNavigation) -> Void
	) -> some View {
		let saveGameHistoryUseCase = SaveGameHistoryUseCase(dataSource: module.gameDataSource)
		let restoreGameHistoryUseCase = RestoreGameHistoryUseCase(dataSource: module.gameDataSource)
		let deleteGameUseCase = DeleteGameUseCase(dataSource: module.gameDataSource)
		let getPlayerAverageTurnUseCase = GetPlayerAverageTurnUseCase(dataSource: module.statisticsDataSource)
		let gameManager = GameManager(
			saveGameHistoryUseCase: saveGameHistoryUseCase,
			restoreGameHistoryUseCase: restoreGameHistoryUseCase,
			getPlayerAverageTurnUseCase: getPlayerAverageTurnUseCase,
			gameSettings: gameSettings
		)
		let viewModel = GameViewModel(
		   gameManager: gameManager,
		   gameSettings: gameSettings,
		   pausedGame: pausedGame,
		   coroutineScope: nil
		)
		let iOSViewModel = IOSGameViewModel(
			viewModel: viewModel,
			onNavigation: onNavigation
		)
		return GameScreen(viewModel: iOSViewModel)
	}
}
