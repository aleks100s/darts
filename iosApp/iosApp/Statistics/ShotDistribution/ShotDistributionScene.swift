import SwiftUI
import shared

internal enum ShotDistributionScene {
	static func create(module: AppModule, player: Player) -> some View {
		let getPlayerShotDistributionUseCase = GetPlayerShotDistributionUseCase(statisticsDataSource: module.statisticsDataSource)
		let viewModel = ShotDistributionViewModel(
			getPlayerShotDistributionUseCase: getPlayerShotDistributionUseCase,
			player: player,
			coroutineScope: nil
		)
		let iOSViewModel = IOSShotDistributionViewModel(viewModel: viewModel)
		return ShotDistributionScreen(viewModel: iOSViewModel)
	}
}
