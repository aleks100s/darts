import SwiftUI

struct CreateGameLockScreenWidgetView: View {
	var body: some View {
		ZStack {
			RingView()
			ViewThatFits(in: .horizontal) {
				VStack {
					Text("Play")
					Text("🎯")
				}
				.padding(.horizontal, 8)
				
				Text("🎯")
			}
		}
		.font(.system(size: 12))
		.widgetURL(.createGame)
		.accessibilityElement()
		.accessibilityLabel("Play darts")
		.accessibilityAddTraits(.isButton)
	}
}
