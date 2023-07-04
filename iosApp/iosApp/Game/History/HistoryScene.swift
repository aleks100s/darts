import SwiftUI
import shared

enum HistoryScene {
	static func create(
		using module: AppModule,
		game: Game,
		onNavigate: @escaping (HistoryNavigation) -> Void
	) -> some View {
		let getGameHistoryUseCase = GetGameHistoryFlowUseCase(dataSource: module.gameDataSource)
		let viewModel = HistoryViewModel(
		   getGameHistoryUseCase: getGameHistoryUseCase,
		   coroutineScope: nil,
		   game: game
		)
		let iOSViewModel = IOSHistoryViewModel(
			viewModel: viewModel,
			onNavigate: onNavigate
		)
		return HistoryScreen(viewModel: iOSViewModel)
	}
}
