import shared
import SwiftUI

internal enum AverageValuesScene {
	static func create(module: AppModule) -> some View {
		let getPlayersUseCase = GetPlayersUseCase(dataSource: module.playerDataSource)
		let getAverageSetScoreUseCase = GetAverageSetScoreUseCase(dataSource: module.statisticsDataSource)
		let getAverageShotValueUseCase = GetAverageShotValueUseCase(dataSource: module.statisticsDataSource)
		let getPlayersAverageValuesUseCase = GetPlayersAverageValuesUseCase(dataSource: module.statisticsDataSource)
		let viewModel = AverageValuesViewModel(
			getPlayersUseCase: getPlayersUseCase,
			getAverageSetScoreUseCase: getAverageSetScoreUseCase,
			getAverageShotValueUseCase: getAverageShotValueUseCase,
			getPlayersAverageValuesUseCase: getPlayersAverageValuesUseCase,
			coroutineScope: nil
		)
		let iosViewModel = IOSAverageValuesViewModel(viewModel: viewModel)
		return AverageValuesView(viewModel: iosViewModel)
	}
}
