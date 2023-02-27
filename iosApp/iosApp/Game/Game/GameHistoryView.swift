import SwiftUI

internal struct GameHistoryView: View {
	let onShowGameInputClick: () -> Void
	
	var body: some View {
		VStack {
			Text("Game History")
			Button {
				onShowGameInputClick()
			} label: {
				Text("back_to_game")
			}
		}
	}
}
