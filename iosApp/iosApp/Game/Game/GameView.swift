import SwiftUI

internal struct GameView: View {
	@StateObject var viewModel: IOSGameViewModel
	
	var body: some View {
		Text("Game")
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
}
