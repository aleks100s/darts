import SwiftUI
import shared

internal enum CreateGameScene {
	@MainActor
	static func create(
		using module: AppModule,
		createPlayer: @escaping () -> (),
		startGame: @escaping ([Player], Int32) -> ()
	) -> some View {
		let getPlayersUseCase = GetPlayersUseCase(dataSource: module.playerDataSource)
		let deletePlayerUseCase = DeletePlayerUseCase(dataSource: module.playerDataSource)
		let viewModel = IOSCreateGameViewModel(
			getPlayersUseCase: getPlayersUseCase,
			deletePlayerUseCase: deletePlayerUseCase,
			createPlayer: createPlayer,
			startGame: startGame
		)
		return CreateGameView(viewModel: viewModel)
	}
}
