import SwiftUI
import shared

struct BiggestFinalTurnScreen: View {
	@StateObject var viewModel: IOSBiggestFinalTurnViewModel
	
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
		if viewModel.state.isLoading {
			LoadingView()
		} else if viewModel.state.playersBiggestFinalTurns.isEmpty {
			NoDataView()
		} else {
			StatisticsPlayerListView(
				values: viewModel.state.playersBiggestFinalTurns.map { ($0.player, "\($0.set.score())") }) { index in
					viewModel.select(turn: viewModel.state.playersBiggestFinalTurns[index].set)
				}
		}
	}
}
