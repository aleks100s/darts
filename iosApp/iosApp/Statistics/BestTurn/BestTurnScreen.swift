import SwiftUI
import shared

internal struct BestTurnScreen: View {
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
		if viewModel.state.playersBestTurns.isEmpty {
			NoDataView()
		} else {
			StatisticsPlayerListView(
				values: viewModel.state.playersBestTurns.map { ($0.player, "\($0.set.score())") },
				onValueSelected: { index in
					viewModel.select(turn: viewModel.state.playersBestTurns[index].set)
				}
			)
		}
	}
}
