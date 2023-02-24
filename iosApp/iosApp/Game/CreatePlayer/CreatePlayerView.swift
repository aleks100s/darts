import SwiftUI

internal struct CreatePlayerView: View {
	@StateObject var viewModel: IOSCreatePlayerViewModel
	@State private var playerName = ""
	
	var body: some View {
		VStack(spacing: 16) {
			TextField("Enter player name", text: $playerName)
			Button {
				viewModel.onEvent(.SavePlayer(name: playerName))
			} label: {
				Text("Save")
			}
			.disabled(playerName.count < 3)
		}
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
}
