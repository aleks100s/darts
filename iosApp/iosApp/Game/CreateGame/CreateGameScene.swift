import SwiftUI
import shared

internal enum CreateGameScene {
	@MainActor
	static func create(using module: AppModule, onEvent: @escaping (CreateGameEvent) -> ()) -> some View {
		let getPlayersUseCase = GetPlayersUseCase(dataSource: module.playerDataSource)
		let deletePlayerUseCase = DeletePlayerUseCase(dataSource: module.playerDataSource)
		let viewModel = IOSCreateGameViewModel(
			getPlayersUseCase: getPlayersUseCase,
			deletePlayerUseCase: deletePlayerUseCase,
			onEvent: onEvent
		)
		return CreateGameView(viewModel: viewModel)
	}
}
