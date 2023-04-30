import shared
import SwiftUI

internal enum AverageValuesScene {
	static func create(module: AppModule) -> some View {
		let getPlayersUseCase = GetPlayersUseCase(dataSource: module.playerDataSource)
		let getAverageTurnScoreUseCase = GetAverageTurnScoreUseCase(dataSource: module.statisticsDataSource)
		let getAverageShotValueUseCase = GetAverageShotValueUseCase(dataSource: module.statisticsDataSource)
		let getPlayersAverageValuesUseCase = GetPlayersAverageValuesUseCase(dataSource: module.statisticsDataSource)
		let viewModel = AverageValuesViewModel(
			getPlayersUseCase: getPlayersUseCase,
			getAverageTurnScoreUseCase: getAverageTurnScoreUseCase,
			getAverageShotValueUseCase: getAverageShotValueUseCase,
			getPlayersAverageValuesUseCase: getPlayersAverageValuesUseCase,
			coroutineScope: nil
		)
		let iosViewModel = IOSAverageValuesViewModel(viewModel: viewModel)
		return AverageValuesScreen(viewModel: iosViewModel)
	}
}
