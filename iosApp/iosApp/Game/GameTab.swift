import SwiftUI

internal struct GameTab: View {
	@State private var navigationStack: [GameNavigation] = []
	
	var body: some View {
		Text("Game")
	}
}
