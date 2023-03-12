import SwiftUI
import shared

internal struct StatisticsView: View {
	let data: [StatisticsEvent] = [
		.ShowBestSet(),
		.ShowMostFrequentShots(),
		.ShowBiggestFinalSet(),
		.ShowAverageValues(),
		.ShowShotDistribution(),
		.ShowVictoryDistribution()
	]
	let onSelect: (StatisticsEvent) -> Void
	
	var body: some View {
		List {
			ForEach(data) { event in
				SignleSelectableItem(title: event.title) {
					onSelect(event)
				}
			}
		}
	}
}
