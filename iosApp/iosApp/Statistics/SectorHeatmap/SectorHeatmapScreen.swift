import SwiftUI
import shared

struct SectorHeatmapScreen: View {
	@StateObject var viewModel: IOSSectorHeatmapViewModel
	
	@Environment(\.displayScale) var displayScale
	@State private var renderedImage = Image(systemName: "photo")
	
	var body: some View {
		viewBody
			.background(Color.surface)
			.toolbar {
				ToolbarItem(placement: .navigationBarTrailing) {
					ShareLink("share", item: renderedImage, preview: SharePreview("preview", image: renderedImage))
				}
			}
			.onReceive(viewModel.$state) { _ in
				Task {
					render()
				}
			}
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
	
	@ViewBuilder
	var viewBody: some View {
		if viewModel.state.isLoading {
			LoadingView()
		} else if let distribution = viewModel.state.distribution {
			ScrollView {
				content(distribution)
			}
		} else {
			NoDataView()
		}
	}
	
	@ViewBuilder
	private func content(_ distribution: SectorHeatmapDistribution) -> some View {
		VStack(alignment: .leading, spacing: 32) {
			HeatmapBoard(heatmap: distribution)
			countList(list: distribution.heatmap)
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
	
	@MainActor
	private func render() {
		guard let distribution = viewModel.state.distribution else { return }
		
		let content = content(distribution)
			.background(Color.background)
			.frame(width: 300)
		let renderer = ImageRenderer(content: content)
		// make sure and use the correct display scale for this device
		renderer.scale = displayScale
		if let uiImage = renderer.uiImage {
			renderedImage = Image(uiImage: uiImage)
		}
	}
}
