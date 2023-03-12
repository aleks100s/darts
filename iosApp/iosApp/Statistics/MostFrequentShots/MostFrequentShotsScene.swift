import SwiftUI
import shared

internal enum MostFrequentShotsScene {
	static func create(
		module: AppModule,
		onTurnSelected: @escaping (Set) -> Void
	) -> some View {
		let getPlayersUseCase = GetPlayersUseCase(dataSource: module.playerDataSource)
		let getPlayersMostFrequentShotsUseCase = GetPlayersMostFrequentShotsUseCase(dataSource: module.statisticsDataSource)
		let viewModel = MostFrequentShotsViewModel(
			getPlayersUseCase: getPlayersUseCase,
			getPlayersMostFrequentShotsUseCase: getPlayersMostFrequentShotsUseCase,
			coroutineScope: nil
		)
		let iOSViewModel = IOSMostFrequentShotsViewModel(viewModel: viewModel, onTurnSelected: onTurnSelected)
		return MostFrequentShotsView(viewModel: iOSViewModel)
	}
}
