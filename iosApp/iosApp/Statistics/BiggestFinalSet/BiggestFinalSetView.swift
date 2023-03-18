import SwiftUI
import shared

internal struct BiggestFinalSetView: View {
	@StateObject var viewModel: IOSBiggestFinalSetViewModel
	
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
		if viewModel.state.playersBiggestFinalSets.isEmpty {
			NoDataView()
		} else {
			StatisticsPlayerListView(
				values: viewModel.state.playersBiggestFinalSets.map { ($0.player, "\($0.set.score())") }) { index in
					viewModel.select(turn: viewModel.state.playersBiggestFinalSets[index].set)
				}
		}
	}
}
