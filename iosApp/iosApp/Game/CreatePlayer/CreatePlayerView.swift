import SwiftUI

internal struct CreatePlayerView: View {
	@StateObject var viewModel: IOSCreatePlayerViewModel
	
	var body: some View {
		VStack {
			Text("Create player")
			Button {
				viewModel.onEvent(.SavePlayer(name: "Kek lol"))
			} label: {
				Text("Save")
			}

		}
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
}
