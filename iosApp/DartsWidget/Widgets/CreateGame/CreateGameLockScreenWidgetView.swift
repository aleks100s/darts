import SwiftUI

struct CreateGameLockScreenWidgetView: View {
	var body: some View {
		ZStack {
			RingView()
			ViewThatFits(in: .horizontal) {
				VStack {
					Text("play")
					Text("🎯")
				}
				.padding(.horizontal, 8)
				
				Text("🎯")
			}
		}
		.font(.system(size: 12))
		.widgetURL(.createGame)
		.accessibilityElement()
		.accessibilityLabel("play_darts")
		.accessibilityAddTraits(.isButton)
	}
}
