import shared
import SwiftUI

enum TimeScene {
	static func create(module: AppModule) -> some View {
		let converter = TimeDurationConverter()
		let getGlobalTotalTimePlayedUseCase = GetGlobalTotalTimePlayedUseCase(
			dataSource: module.statisticsDataSource,
			converter: converter
		)
		let getPlayersTotalTimePlayedUseCase = GetPlayersTotalTimePlayedUseCase(
			dataSource: module.statisticsDataSource,
			converter: converter
		)
		let getPlayersUseCase = GetPlayersUseCase(dataSource: module.playerDataSource)
		let viewModel = TimeViewModel(
			getGlobalTotalTimePlayedUseCase: getGlobalTotalTimePlayedUseCase,
			getPlayersTotalTimePlayedUseCase: getPlayersTotalTimePlayedUseCase,
			getPlayersUseCase: getPlayersUseCase,
			coroutineScope: nil
		)
		let iOSViewModel = IOSTimeViewModel(viewModel: viewModel)
		return TimeScreen(viewModel: iOSViewModel)
	}
}
