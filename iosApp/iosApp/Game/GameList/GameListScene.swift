import shared
import SwiftUI

internal enum GameListScene {
	@MainActor
	static func create(
		using module: AppModule,
		onGameSelected: @escaping (Game) -> Void
	) -> some View {
		let getGamesUseCase = GetGamesUseCase(dataSource: module.gameDataSource)
		let deleteGameUseCase = DeleteGameUseCase(dataSource: module.gameDataSource)
		let viewModel = IOSGameListViewModel(
			getGamesUseCase: getGamesUseCase,
			deleteGameUseCase: deleteGameUseCase,
			onGameSelected: onGameSelected
		)
		let view = GameListView(viewModel: viewModel)
		return view
	}
}
