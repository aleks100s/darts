import SwiftUI
import shared

internal struct SectorHeatmapView: View {
	@StateObject var viewModel: IOSSectorHeatmapViewModel
	
	var body: some View {
		content
			.background(Color.surface)
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
	
	@ViewBuilder
	var content: some View {
		if let distribution = viewModel.state.distribution {
			ScrollView {
				VStack(alignment: .leading, spacing: 32) {
					HeatmapBoard(heatmap: distribution)
					countList(list: distribution.heatmap)
				}
			}
		} else {
			NoDataView()
		}
	}
	
	@ViewBuilder
	private func countList(list: [SectorHeat]) -> some View {
		VStack(alignment: .leading) {
			ForEach(list) { sectorHeat in
				sectorHeatItem(heat: sectorHeat)
			}
		}
		.frame(maxWidth: .infinity)
		.padding(.horizontal, 16)
	}
	
	@ViewBuilder
	private func sectorHeatItem(heat: SectorHeat) -> some View {
		HStack(alignment: .center) {
			Image(systemName: "square.fill")
				.foregroundColor(heat.color)
			Text("sector_count \(heat.sector.valueString()) \(heat.count)")
			Spacer()
		}
	}
}
