import SwiftUI
import shared

internal struct ContentView: View {
	let module: AppModule
	
	var body: some View {
		TabView {
			GameTab(module: module)
				.tabItem {
					Label("Game", systemImage: "play")
				}
			
			StatisticsTab(module: module)
				.tabItem {
					Label("Statistics", systemImage: "chart.pie")
				}
		}
	}
}
