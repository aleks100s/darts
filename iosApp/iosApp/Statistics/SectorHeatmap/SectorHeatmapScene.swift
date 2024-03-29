import SwiftUI
import shared

enum SectorHeatmapScene {
	static func create(module: AppModule, player: Player) -> some View {
		let getSectorHeatmapUseCase = GetSectorHeatmapUseCase(dataSource: module.statisticsDataSource)
		let viewModel = SectorHeatmapViewModel(
			getSectorHeatmapUseCase: getSectorHeatmapUseCase,
			player: player,
			coroutineScope: nil
		)
		let iOSViewModel = IOSSectorHeatmapViewModel(viewModel: viewModel)
		return SectorHeatmapScreen(viewModel: iOSViewModel)
	}
}
