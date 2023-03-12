import SwiftUI
import shared

internal struct BestTurnView: View {
	@StateObject var viewModel: IOSBestTurnViewModel
	
	var body: some View {
		StatisticsPlayerListView(
			values: viewModel.state.playersBestSets.map { ($0.player, "\($0.set.score())") },
			onValueSelected: { index in
				viewModel.select(turn: viewModel.state.playersBestSets[index].set)
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
