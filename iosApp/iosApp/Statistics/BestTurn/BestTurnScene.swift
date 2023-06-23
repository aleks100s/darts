import shared
import SwiftUI

enum BestTurnScene {
	static func create(
		module: AppModule,
		onTurnSelected: @escaping (Turn) -> Void
	) -> some View {
		let getPlayersUseCase = GetPlayersUseCase(dataSource: module.playerDataSource)
		let getPlayersBestTurnsUseCase = GetPlayersBestTurnsUseCase(dataSource: module.statisticsDataSource)
		let viewModel = BestTurnViewModel(
			getPlayersUseCase: getPlayersUseCase,
			getPlayersBestTurnsUseCase: getPlayersBestTurnsUseCase,
			coroutineScope: nil
		)
		let iOSViewModel = IOSBestTurnViewModel(viewModel: viewModel, onTurnSelected: onTurnSelected)
		return BestTurnScreen(viewModel: iOSViewModel)
	}
}
