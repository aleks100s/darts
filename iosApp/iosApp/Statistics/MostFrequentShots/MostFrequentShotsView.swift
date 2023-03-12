import SwiftUI
import shared

internal struct MostFrequentShotsView: View {
	@StateObject var viewModel: IOSMostFrequentShotsViewModel
	
	var body: some View {
		StatisticsPlayerListView(
			values: viewModel.state.mostFrequentShots.map { ($0.player, "") },
			onValueSelected: { index in
				viewModel.select(turn: Set(shots: viewModel.state.mostFrequentShots[index].shots, leftAfter: 0, isOverkill: false))
			}
		)
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
}
