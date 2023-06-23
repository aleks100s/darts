import SwiftUI
import shared

enum GameScene {
	static func create(
		using module: AppModule,
		gameSettings: GameSettings,
		onGameFinished: @escaping () -> Void,
		onShowInGameHistory: @escaping ([PlayerHistory], Int32, Int) -> Void,
		onTurnSelected: @escaping (Turn) -> Void,
		onGameReplaySelected: @escaping () -> Void,
		onShowGameSettings: @escaping () -> Void
	) -> some View {
		let saveGameHistoryUseCase = SaveGameHistoryUseCase(dataSource: module.gameDataSource)
		let getPlayerAverageTurnUseCase = GetPlayerAverageTurnUseCase(dataSource: module.statisticsDataSource)
		let gameManager = GameManager(
			saveGameHistoryUseCase: saveGameHistoryUseCase,
			getPlayerAverageTurnUseCase: getPlayerAverageTurnUseCase,
			gameSettings: gameSettings
		)
		let viewModel = GameViewModel(
		   gameManager: gameManager,
		   gameSettings: gameSettings,
		   coroutineScope: nil
		)
		let iOSViewModel = IOSGameViewModel(
			viewModel: viewModel,
			onGameFinished: onGameFinished,
			onShowInGameHistory: onShowInGameHistory,
			onTurnSelected: onTurnSelected,
			onGameReplaySelected: onGameReplaySelected,
			onShowGameSettings: onShowGameSettings
		)
		return GameScreen(viewModel: iOSViewModel)
	}
}
