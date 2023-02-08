import SwiftUI

internal struct GameListView: View {
	@StateObject var viewModel: IOSGameListViewModel
	
	var body: some View {
		Text("GameList")
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
}
