import SwiftUI

internal struct CreatePlayerView: View {
	@StateObject var viewModel: IOSCreatePlayerViewModel
	@State private var playerName = ""
	
	var body: some View {
		VStack(spacing: 16) {
			TextField("enter_new_player_name", text: $playerName)
			Button {
				viewModel.onEvent(.SavePlayer(name: playerName))
			} label: {
				Text("create_player")
			}
			.disabled(playerName.count < 3)
		}
		.padding()
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
}
