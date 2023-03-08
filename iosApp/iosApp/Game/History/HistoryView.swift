import SwiftUI

internal struct HistoryView: View {
	@StateObject var viewModel: IOSHistoryViewModel
	
	var body: some View {
		GameHistoryView(
			gameHistory: viewModel.state.gameHistory,
			gameGoal: viewModel.state.gameGoal,
			onTurnSelected: viewModel.select
		)
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
}
