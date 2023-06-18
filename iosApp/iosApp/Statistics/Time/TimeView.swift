import SwiftUI
import shared

struct TimeView: View {
	@StateObject var viewModel: IOSTimeViewModel
	
	var body: some View {
		content
			.background(Color.background)
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
	
	@ViewBuilder
	var content: some View {
		if viewModel.state.isLoading {
			LoadingView()
		} else if viewModel.state.isEmpty {
			NoDataView()
		} else {
			ScrollView(showsIndicators: false) {
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
		}
	}
	
	@ViewBuilder
	private func item(title: String, value: TimeDuration) -> some View {
		TableRow(columns: [
			title,
			String(localized: "time_duration \(value.hours) \(value.minutes) \(value.seconds)")
		])
	}
}
