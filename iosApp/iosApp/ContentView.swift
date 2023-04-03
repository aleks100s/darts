import SwiftUI
import shared

internal struct ContentView: View {
	private enum Tab: Hashable {
		case game
		case statistics
	}
	
	let module: AppModule
	
	@State private var selection = Tab.game
	
	var body: some View {
		TabView(selection: $selection) {
			GameTab(module: module)
				.tabItem {
					Label("Game", systemImage: "play")
				}
				.tag(Tab.game)
			
			StatisticsTab(module: module)
				.tabItem {
					Label("Statistics", systemImage: "chart.pie")
				}
				.tag(Tab.statistics)
		}
		.onOpenURL { url in
			if url.canBeNavigated(to: GameNavigation.createGame.urlComponent)  {
				selection = .game
			}
		}
	}
}
