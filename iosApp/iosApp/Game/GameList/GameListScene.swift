import shared
import SwiftUI

internal enum GameListScene {
	@MainActor
	static func create(using module: AppModule) -> some View {
		let getGamesUseCase = GetGamesUseCase(dataSource: module.gameDataSource)
		let deleteGameUseCase = DeleteGameUseCase(dataSource: module.gameDataSource)
		let viewModel = IOSGameListViewModel(
			getGamesUseCase: getGamesUseCase,
			deleteGameUseCase: deleteGameUseCase
		)
		let view = GameListView(viewModel: viewModel)
		return view
	}
}
