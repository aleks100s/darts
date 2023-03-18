import SwiftUI
import shared

internal struct BestTurnView: View {
	@StateObject var viewModel: IOSBestTurnViewModel
	
	var body: some View {
		content
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
	
	@ViewBuilder
	private var content: some View {
		if viewModel.state.playersBestSets.isEmpty {
			NoDataView()
		} else {
			StatisticsPlayerListView(
				values: viewModel.state.playersBestSets.map { ($0.player, "\($0.set.score())") },
				onValueSelected: { index in
					viewModel.select(turn: viewModel.state.playersBestSets[index].set)
				}
			)
		}
	}
}
