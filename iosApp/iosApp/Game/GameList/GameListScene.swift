import shared
import SwiftUI

internal enum GameListScene {
	@MainActor
	static func create(
		using module: AppModule,
		onGameSelected: @escaping (Game) -> Void,
		onReplay: @escaping (Game) -> Void
	) -> some View {
		let getGamesUseCase = GetGamesUseCase(dataSource: module.gameDataSource)
		let deleteGameUseCase = DeleteGameUseCase(dataSource: module.gameDataSource)
		let viewModel = GameListViewModel(
			deleteGameUseCase: deleteGameUseCase,
			getGamesUseCase: getGamesUseCase,
			coroutineScope: nil
		)
		let iOSViewModel = IOSGameListViewModel(
			viewModel: viewModel,
			onGameSelected: onGameSelected,
			onReplay: onReplay
		)
		let view = GameListScreen(viewModel: iOSViewModel)
		return view
	}
}
