import SwiftUI
import shared

internal enum VictoryDistributionScene {
	static func create(module: AppModule, player: Player) -> some View {
		let getPlayerVictoryDistributionUseCase = GetPlayerVictoryDistributionUseCase(dataSource: module.statisticsDataSource)
		let viewModel = VictoryDistributionViewModel(
			getPlayerVictoryDistributionUseCase: getPlayerVictoryDistributionUseCase,
			player: player,
			coroutineScope: nil
		)
		let iOSViewModel = IOSVictoryDistributionViewModel(viewModel: viewModel)
		return VictoryDistributionView(viewModel: iOSViewModel)
	}
}
