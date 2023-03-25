import SwiftUI
import shared

internal enum SectorHeatmapScene {
	static func create(module: AppModule, player: Player) -> some View {
		let getSectorHeatmapUseCase = GetSectorHeatmapUseCase(dataSource: module.statisticsDataSource)
		let viewModel = SectorHeatmapViewModel(
			getSectorHeatmapUseCase: getSectorHeatmapUseCase,
			player: player,
			coroutineScope: nil
		)
		let iOSViewModel = IOSSectorHeatmapViewModel(viewModel: viewModel)
		return SectorHeatmapView(viewModel: iOSViewModel)
	}
}
