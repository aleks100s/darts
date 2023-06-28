import SwiftUI

struct AverageValuesScreen: View {
	@StateObject var viewModel: IOSAverageValuesViewModel
	
	@Environment(\.displayScale) var displayScale
	@State private var renderedImage = Image(systemName: "photo")
	
	var body: some View {
		viewBody
			.background(Color.background)
			.toolbar {
				ToolbarItem(placement: .navigationBarTrailing) {
					ShareLink("share", item: renderedImage, preview: SharePreview("preview", image: renderedImage))
						.disabled(viewModel.state.isLoading)
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
	private var viewBody: some View {
		if viewModel.state.isLoading {
			LoadingView()
		} else if viewModel.state.isEmpty() {
			NoDataView()
		} else {
			ScrollView(showsIndicators: false) {
				content
			}
		}
	}
	
	@ViewBuilder
	private var content: some View {
		VStack(spacing: 0) {
			header
			
			averageValues(
				title: String(localized: "all_players"),
				avgTurnScore: viewModel.state.averageTurnOfAll,
				avgShotScore: viewModel.state.averageShotOfAll
			)
			
			ForEach(viewModel.state.playersAverageValues) { value in
				averageValues(
					title: value.player.name,
					avgTurnScore: value.setScore,
					avgShotScore: value.shotValue
				)
			}
		}
	}
	
	@ViewBuilder
	private var header: some View {
		TableHeader(columns: [
			String(localized: "player"),
			String(localized: "average_set"),
			String(localized: "average_shot")
		])
	}
	
	@ViewBuilder
	private func averageValues(
		title: String,
		avgTurnScore: Double,
		avgShotScore: Double
	) -> some View {
		TableRow(columns: [
			title,
			String(format: "%.2f", avgTurnScore),
			String(format: "%.2f", avgShotScore)
		])
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
