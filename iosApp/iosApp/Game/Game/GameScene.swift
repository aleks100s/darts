import SwiftUI
import shared

internal enum GameScene {
	static func create(
		using module: AppModule,
		players: [Player],
		goal: Int32
	) -> some View {
		let saveGameHistoryUseCase = SaveGameHistoryUseCase(dataSource: module.gameDataSource)
		let gameManager = GameManager(
			saveGameHistoryUseCase: saveGameHistoryUseCase,
			players: players,
			goal: goal
		)
		let viewModel = IOSGameViewModel(gameManager: gameManager)
		return GameView(viewModel: viewModel)
	}
}
