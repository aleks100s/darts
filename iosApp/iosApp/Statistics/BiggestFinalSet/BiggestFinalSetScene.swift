import SwiftUI
import shared

internal enum BiggestFinalSetScene {
	static func create(
		module: AppModule,
		onTurnSelected: @escaping (Set) -> Void
	) -> some View {
		let getPlayersUseCase = GetPlayersUseCase(dataSource: module.playerDataSource)
		let getPlayersBiggestFinalSetUseCase = GetPlayersBiggestFinalSetUseCase(dataSource: module.statisticsDataSource)
		let viewModel = BiggestFinalSetViewModel(
			getPlayersUseCase: getPlayersUseCase,
			getPlayersBiggestFinalSetUseCase: getPlayersBiggestFinalSetUseCase,
			coroutineScope: nil
		)
		let iOSViewModel = IOSBiggestFinalSetViewModel(viewModel: viewModel, onTurnSelected: onTurnSelected)
		return BiggestFinalSetView(viewModel: iOSViewModel)
	}
}
