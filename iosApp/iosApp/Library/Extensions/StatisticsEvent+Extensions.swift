import shared

extension StatisticsEvent: Identifiable {}

extension StatisticsEvent {
	var title: String {
		switch self {
		case .ShowVictoryDistribution():
			return String(localized: "victory_distribution_statistics")
			
		case .ShowShotDistribution():
			return String(localized: "shot_distribution_statistics")
			
		case .ShowBiggestFinalSet():
			return String(localized: "biggest_final_set_statistics")
			
		case .ShowMostFrequentShots():
			return String(localized: "most_frequent_shots_statistics")
			
		case .ShowBestSet():
			return String(localized: "best_set_statistics")
			
		case .ShowAverageValues():
			return String(localized: "average_values_statistics")
			
		case .ShowSectorHeatmapDistribution():
			return String(localized: "sector_heatmap_statistics")
			
		default:
			return ""
		}
	}
}
