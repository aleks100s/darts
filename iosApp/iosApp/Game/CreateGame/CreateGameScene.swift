import SwiftUI
import shared

internal enum CreateGameScene {
	@MainActor
	static func create(
		using module: AppModule,
		startGame: @escaping ([Player], Int32, Bool) -> ()
	) -> some View {
		let getPlayersUseCase = GetPlayersUseCase(dataSource: module.playerDataSource)
		let deletePlayerUseCase = DeletePlayerUseCase(dataSource: module.playerDataSource)
		let createGameViewModel = CreateGameViewModel(
			deletePlayerUseCase: deletePlayerUseCase,
			getPlayersUseCase: getPlayersUseCase,
			coroutineScope: nil
		)
		let createPlayerUseCase = CreatePlayerUseCase(dataSource: module.playerDataSource)
		let createPlayerViewModel = CreatePlayerViewModel(
			createPlayerUseCase: createPlayerUseCase,
			getPlayersUseCase: getPlayersUseCase,
			coroutineScope: nil
		)
		let viewModel = IOSCreateGameViewModel(
			viewModel: createGameViewModel,
			createPlayerViewModel: createPlayerViewModel,
			startGame: startGame
		)
		return CreateGameView(viewModel: viewModel)
	}
}
