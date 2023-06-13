import shared
import SwiftUI

internal enum GameListScene {
	@MainActor
	static func create(
		using module: AppModule,
		onCreateGame: @escaping () -> Void,
		onGameSelected: @escaping (Game) -> Void,
		onReplay: @escaping (Game) -> Void,
		onShowCalculator: @escaping () -> Void
	) -> some View {
		let getGamesUseCase = GetGamesUseCase(dataSource: module.gameDataSource)
		let deleteGameUseCase = DeleteGameUseCase(dataSource: module.gameDataSource)
		let createPlayerUseCase = CreatePlayerUseCase(dataSource: module.playerDataSource)
		let saveGameHistoryUseCase = SaveGameHistoryUseCase(dataSource: module.gameDataSource)
		let prepopulateDatabaseUseCase = PrepopulateDatabaseUseCase(
			createPlayerUseCase: createPlayerUseCase,
			saveGameHistoryUseCase: saveGameHistoryUseCase
		)
		let viewModel = GameListViewModel(
			deleteGameUseCase: deleteGameUseCase,
			prepopulateDatabaseUseCase: prepopulateDatabaseUseCase,
			getGamesUseCase: getGamesUseCase,
			coroutineScope: nil
		)
		let iOSViewModel = IOSGameListViewModel(
			viewModel: viewModel,
			onCreateGame: onCreateGame,
			onGameSelected: onGameSelected,
			onReplay: onReplay,
			onShowCalculator: onShowCalculator
		)
		let view = GameListScreen(viewModel: iOSViewModel)
		return view
	}
}
