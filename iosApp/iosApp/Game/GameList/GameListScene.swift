import shared
import SwiftUI

enum GameListScene {
	@MainActor
	static func create(
		using module: AppModule,
		onNavigation: @escaping (GameListNavigation) -> Void
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
			onNavigation: onNavigation
		)
		let view = GameListScreen(viewModel: iOSViewModel)
		return view
	}
}
