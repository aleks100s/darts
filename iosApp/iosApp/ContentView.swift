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
					Label("game", systemImage: "play")
				}
				.tag(Tab.game)
				.accessibilityIdentifier("tabPlay")
			
			StatisticsTab(module: module)
				.tabItem {
					Label("statistics", systemImage: "chart.pie")
				}
				.tag(Tab.statistics)
				.accessibilityIdentifier("tapStatistics")
		}
		.onOpenURL { url in
			if url.canBeNavigated(to: GameNavigation.createGame.urlComponent)  {
				selection = .game
			}
		}
		.onAppear {
			// correct the transparency bug for Tab bars
			let tabBarAppearance = UITabBarAppearance()
			tabBarAppearance.configureWithOpaqueBackground()
			UITabBar.appearance().scrollEdgeAppearance = tabBarAppearance
		}
	}
}
