import SwiftUI
import shared

struct TimeScreen: View {
	@StateObject var viewModel: IOSTimeViewModel
	
	@Environment(\.displayScale) var displayScale
	@State private var renderedImage = Image(systemName: "photo")
	
	var body: some View {
		viewContent
			.background(Color.background)
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
	var viewContent: some View {
		if viewModel.state.isLoading {
			LoadingView()
		} else if viewModel.state.isEmpty {
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
			TableHeader(columns: [
				String(localized: "player"),
				String(localized: "time_statistics")
			])
			if let totalTimePlayed = viewModel.state.totalTimePlayed {
				item(title: String(localized: "all_players"), value: totalTimePlayed)
			}
			ForEach(viewModel.state.playersDuration) { duration in
				item(title: duration.player.name, value: duration.duration)
			}
		}
	}
	
	@ViewBuilder
	private func item(title: String, value: TimeDuration) -> some View {
		TableRow(columns: [
			title,
			String(localized: "time_duration \(value.hours) \(value.minutes) \(value.seconds)")
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
