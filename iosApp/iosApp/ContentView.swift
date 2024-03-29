import SwiftUI
import shared

struct ContentView: View {
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
					Label("game", systemImage: "gamecontroller")
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
			if url.canBeNavigated(to: GameTabNavigation.createGame.urlComponent)
				|| url.canBeNavigated(to: GameTabNavigation.calculator.urlComponent) {
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
