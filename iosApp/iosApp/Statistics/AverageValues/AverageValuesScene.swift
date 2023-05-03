import shared
import SwiftUI

internal enum AverageValuesScene {
	static func create(module: AppModule) -> some View {
		let getPlayersUseCase = GetPlayersUseCase(dataSource: module.playerDataSource)
		let getOverallAverageTurnScoreUseCase = GetOverallAverageTurnScoreUseCase(dataSource: module.statisticsDataSource)
		let getOverallAverageShotValueUseCase = GetOverallAverageShotValueUseCase(dataSource: module.statisticsDataSource)
		let getPlayersAverageValuesUseCase = GetPlayersAverageValuesUseCase(dataSource: module.statisticsDataSource)
		let viewModel = AverageValuesViewModel(
			getPlayersUseCase: getPlayersUseCase,
			getOverallAverageTurnScoreUseCase: getOverallAverageTurnScoreUseCase,
			getOverallAverageShotValueUseCase: getOverallAverageShotValueUseCase,
			getPlayersAverageValuesUseCase: getPlayersAverageValuesUseCase,
			coroutineScope: nil
		)
		let iosViewModel = IOSAverageValuesViewModel(viewModel: viewModel)
		return AverageValuesScreen(viewModel: iosViewModel)
	}
}
