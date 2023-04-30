import SwiftUI

internal struct PlayerListScreen: View {
	@StateObject var viewModel: IOSPlayerListViewModel
	
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
		if viewModel.state.players.isEmpty {
			NoDataView()
		} else {
			StatisticsPlayerListView(
				values: viewModel.state.players.map { ($0, "") },
				onValueSelected: { index in
					viewModel.select(player: viewModel.state.players[index])
				}
			)
		}
	}
}
