import shared
import SwiftUI

internal enum BestTurnScene {
	static func create(
		module: AppModule,
		onTurnSelected: @escaping (Set) -> Void
	) -> some View {
		let getPlayersUseCase = GetPlayersUseCase(dataSource: module.playerDataSource)
		let getPlayersBestSetsUseCase = GetPlayersBestSetsUseCase(dataSource: module.statisticsDataSource)
		let viewModel = BestSetViewModel(
			getPlayersUseCase: getPlayersUseCase,
			getPlayersBestSetsUseCase: getPlayersBestSetsUseCase,
			coroutineScope: nil
		)
		let iOSViewModel = IOSBestTurnViewModel(viewModel: viewModel, onTurnSelected: onTurnSelected)
		return BestTurnView(viewModel: iOSViewModel)
	}
}
