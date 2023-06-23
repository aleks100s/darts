import SwiftUI
import shared

struct StatisticsScreen: View {
	let data: [StatisticsEvent] = [
		.ShowBestTurn(),
		.ShowBiggestFinalTurn(),
		.ShowAverageValues(),
		.ShowShotDistribution(),
		.ShowVictoryDistribution(),
		.ShowSectorHeatmapDistribution(),
		.ShowTimeStatistics()
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
