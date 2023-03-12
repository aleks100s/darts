import SwiftUI
import shared

internal enum PlayerListScene {
	static func create(
		module: AppModule,
		onPlayerSelected: @escaping (Player) -> Void
	) -> some View {
		let getPlayersUseCase = GetPlayersUseCase(dataSource: module.playerDataSource)
		let viewModel = PlayerListViewModel(
			getPlayersUseCase: getPlayersUseCase,
			coroutineScope: nil
		)
		let iOSViewModel = IOSPlayerListViewModel(
			viewModel: viewModel,
			onPlayerSelected: onPlayerSelected
		)
		return PlayerListView(viewModel: iOSViewModel)
	}
}
