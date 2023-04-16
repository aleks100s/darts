import SwiftUI
import shared

internal enum HistoryScene {
	static func create(
		using module: AppModule,
		game: Game,
		onTurnSelected: @escaping (Turn) -> Void,
		onShowRecap: @escaping (HistoryState) -> Void
	) -> some View {
		let getGameHistoryUseCase = GetGameHistoryUseCase(dataSource: module.gameDataSource)
		let viewModel = HistoryViewModel(
		   getGameHistoryUseCase: getGameHistoryUseCase,
		   coroutineScope: nil,
		   gameId: game.id?.int64Value ?? 0,
		   gameGoal: game.gameGoal,
		   players: game.players
		)
		let iOSViewModel = IOSHistoryViewModel(
			viewModel: viewModel,
			onTurnSelected: onTurnSelected,
			onShowRecap: onShowRecap
		)
		return HistoryView(viewModel: iOSViewModel)
	}
}
