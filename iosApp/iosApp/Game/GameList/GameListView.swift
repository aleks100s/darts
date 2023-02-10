import SwiftUI

internal struct GameListView: View {
	@StateObject var viewModel: IOSGameListViewModel
	
	var body: some View {
		Text("\(viewModel.state.games.count)")
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
}
