import SwiftUI
import Charts

struct VictoryDistributionScreen: View {
	@StateObject var viewModel: IOSVictoryDistributionViewModel
	
	@Environment(\.displayScale) var displayScale
	@State private var renderedImage = Image(systemName: "photo")
	
	var body: some View {
		viewBody
			.background(Color.background)
			.toolbar {
				ToolbarItem(placement: .navigationBarTrailing) {
					ShareLink("share", item: renderedImage, preview: SharePreview("preview", image: renderedImage))
				}
			}
			.task {
				do {
					try await Task.sleep(nanoseconds: 1_000_000_000)
					render()
				} catch {}
			}
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
	
	@ViewBuilder
	private var viewBody: some View {
		if viewModel.isLoading {
			LoadingView()
		} else if viewModel.totalCount == 0 {
			NoDataView()
		} else {
			ScrollView {
				content
			}
		}
	}
	
	@ViewBuilder
	private var content: some View {
		VStack(spacing: 32) {
			ZStack {
				Pie(slices: $viewModel.data)
				Text("games_count \(viewModel.totalCount)")
			}
			ChartLegend(legend: $viewModel.legend)
		}
		.padding()
	}
	
	@MainActor
	private func render() {
		let content = content
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
