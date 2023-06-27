enum StatisticsNavigationEvent: String, CaseIterable, Identifiable {
	case showBestTurn
	case showBiggestFinalTurn
	case showAverageValues
	case showShotDistribution
	case showVictoryDistribution
	case showSectorHeatmapDistribution
	case showTimeStatistics
	
	var id: String {
		self.rawValue
	}
}
