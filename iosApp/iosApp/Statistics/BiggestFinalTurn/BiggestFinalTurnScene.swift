import SwiftUI
import shared

internal enum BiggestFinalTurnScene {
	static func create(
		module: AppModule,
		onTurnSelected: @escaping (Turn) -> Void
	) -> some View {
		let getPlayersUseCase = GetPlayersUseCase(dataSource: module.playerDataSource)
		let getPlayersBiggestFinalTurnUseCase = GetPlayersBiggestFinalTurnUseCase(dataSource: module.statisticsDataSource)
		let viewModel = BiggestFinalTurnViewModel(
			getPlayersUseCase: getPlayersUseCase,
			getPlayersBiggestFinalTurnUseCase: getPlayersBiggestFinalTurnUseCase,
			coroutineScope: nil
		)
		let iOSViewModel = IOSBiggestFinalTurnViewModel(viewModel: viewModel, onTurnSelected: onTurnSelected)
		return BiggestFinalTurnView(viewModel: iOSViewModel)
	}
}
