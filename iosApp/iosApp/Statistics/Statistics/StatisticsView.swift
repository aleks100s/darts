import SwiftUI
import shared

internal struct StatisticsView: View {
	let data: [StatisticsEvent] = [
		.ShowBestSet(),
		.ShowBiggestFinalSet(),
		.ShowAverageValues(),
		.ShowShotDistribution(),
		.ShowVictoryDistribution(),
		.ShowSectorHeatmapDistribution()
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
