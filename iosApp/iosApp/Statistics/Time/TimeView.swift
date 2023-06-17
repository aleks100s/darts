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
					if let totalTimePlayed = viewModel.state.totalTimePlayed {
						item(title: String(localized: "all_players"), value: totalTimePlayed)
					}
				}
			}
		}
	}
	
	@ViewBuilder
	private func item(title: String, value: TimeDuration) -> some View {
		HStack {
			Text(title)
			Spacer()
			Text("time_duration \(value.hours) \(value.minutes) \(value.seconds)")
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 4)
	}
}
