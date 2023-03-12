import SwiftUI

internal struct PlayerListView: View {
	@StateObject var viewModel: IOSPlayerListViewModel
	
	var body: some View {
		StatisticsPlayerListView(
			values: viewModel.state.players.map { ($0, "") },
			onValueSelected: { index in
				viewModel.select(player: viewModel.state.players[index])
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
