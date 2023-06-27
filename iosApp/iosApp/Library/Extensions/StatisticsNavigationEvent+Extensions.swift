import shared

extension StatisticsNavigationEvent {
	var title: String {
		switch self {
		case .showVictoryDistribution:
			return String(localized: "victory_distribution_statistics")
			
		case .showShotDistribution:
			return String(localized: "shot_distribution_statistics")
			
		case .showBiggestFinalTurn:
			return String(localized: "biggest_final_set_statistics")
			
		case .showBestTurn:
			return String(localized: "best_set_statistics")
			
		case .showAverageValues:
			return String(localized: "average_values_statistics")
			
		case .showSectorHeatmapDistribution:
			return String(localized: "sector_heatmap_statistics")
			
		case .showTimeStatistics:
			return String(localized: "time_statistics")
		}
	}
}
