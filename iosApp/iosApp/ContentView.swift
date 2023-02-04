import SwiftUI

internal struct ContentView: View {
	var body: some View {
		TabView {
			GameTab()
				.tabItem {
					Label("Game", systemImage: "play")
				}
			
			StatisticsTab()
				.tabItem {
					Label("Statistics", systemImage: "chart.pie")
				}
		}
	}
}
