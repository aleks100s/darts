import SwiftUI
import shared

internal struct StatisticsTab: View {
	let module: AppModule
	
	@State private var navigationStack: [StatisticsNavigation] = []
	
	var body: some View {
		NavigationStack(path: $navigationStack) {
			StatisticsView(
				onSelect: { event in
					switch event {
					case .ShowVictoryDistribution():
						navigationStack.append(.victoryDistribution)
						
					case .ShowShotDistribution():
						navigationStack.append(.shotDistribution)
						
					case .ShowBiggestFinalSet():
						navigationStack.append(.biggestFinalSet)
						
					case .ShowMostFrequentShots():
						navigationStack.append(.mostFrequentShots)
						
					case .ShowBestSet():
						navigationStack.append(.bestSet)
						
					case .ShowAverageValues():
						navigationStack.append(.averageValues)
						
					default:
						break
					}
				}
			)
				.navigationTitle("statistics")
				.navigationDestination(for: StatisticsNavigation.self) {
					navigate(to: $0)
				}
		}
	}
	
	@MainActor
	@ViewBuilder
	private func navigate(to scene: StatisticsNavigation) -> some View {
		switch scene {
		case .victoryDistribution:
			Text("victory_distribution_statistics")
			
		case .shotDistribution:
			Text("shot_distribution_statistics")
			
		case .biggestFinalSet:
			Text("biggest_final_set_statistics")
			
		case .mostFrequentShots:
			Text("most_frequent_shots_statistics")
			
		case .bestSet:
			Text("best_set_statistics")
			
		case .averageValues:
			AverageValuesScene.create(module: module)
				.navigationTitle("average_values_statistics")
				.navigationBarTitleDisplayMode(.inline)
		}
	}
}
