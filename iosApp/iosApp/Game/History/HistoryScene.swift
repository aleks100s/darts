import SwiftUI
import shared

internal enum HistoryScene {
	static func create(
		using module: AppModule,
		game: Game
	) -> some View {
		let getGameHistoryUseCase = GetGameHistoryUseCase(dataSource: module.gameDataSource)
		let viewModel = HistoryViewModel(
		   getGameHistoryUseCase: getGameHistoryUseCase,
		   coroutineScope: nil,
		   gameId: game.id?.int64Value ?? 0,
		   gameGoal: game.gameGoal,
		   players: game.players
		)
		let iOSViewModel = IOSHistoryViewModel(viewModel: viewModel)
		return HistoryView(viewModel: iOSViewModel)
	}
}
