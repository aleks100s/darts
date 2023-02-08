import SwiftUI
import shared

internal struct GameTab: View {
	let module: AppModule
	
	@State private var navigationStack: [GameNavigation] = []
	
	var body: some View {
		NavigationStack(path: $navigationStack) {
			GameListScene.create(using: module)
		}
	}
}
