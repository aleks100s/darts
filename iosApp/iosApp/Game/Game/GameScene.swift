import SwiftUI
import shared

internal enum GameScene {
	static func create(
		using module: AppModule,
		players: [Player],
		goal: Int32,
		isFinishWithDoublesChecked: Bool,
		onGameFinished: @escaping () -> Void,
		onShowInGameHistory: @escaping ([PlayerHistory], Int32, Int) -> Void,
		onTurnSelected: @escaping (Set) -> Void
	) -> some View {
		let saveGameHistoryUseCase = SaveGameHistoryUseCase(dataSource: module.gameDataSource)
		let gameManager = GameManager(
			saveGameHistoryUseCase: saveGameHistoryUseCase,
			players: players,
			goal: goal,
			finishWithDoubles: isFinishWithDoublesChecked
		)
		let viewModel = GameViewModel(
		   gameManager: gameManager,
		   coroutineScope: nil
		)
		let iOSViewModel = IOSGameViewModel(
			viewModel: viewModel,
			onGameFinished: onGameFinished,
			onShowInGameHistory: onShowInGameHistory,
			onTurnSelected: onTurnSelected
		)
		return GameView(viewModel: iOSViewModel)
	}
}
