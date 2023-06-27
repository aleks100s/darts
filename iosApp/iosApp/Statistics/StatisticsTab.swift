import SwiftUI
import shared

struct StatisticsTab: View {
	let module: AppModule
	
	@State private var navigationStack: [StatisticsNavigation] = []
	
	var body: some View {
		NavigationStack(path: $navigationStack) {
			StatisticsScreen(
				onSelect: { event in
					switch event {
					case .showVictoryDistribution:
						navigationStack.append(.players(.victoryDistribution))
						
					case .showShotDistribution:
						navigationStack.append(.players(.shotDistribution))
						
					case .showBiggestFinalTurn:
						navigationStack.append(.biggestFinalTurn)
						
					case .showBestTurn:
						navigationStack.append(.bestTurn)
						
					case .showAverageValues:
						navigationStack.append(.averageValues)
						
					case .showSectorHeatmapDistribution:
						navigationStack.append(.players(.heatmapDistribution))
						
					case .showTimeStatistics:
						navigationStack.append(.time)
						
					default:
						return
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
		case let .heatmapDistribution(player):
			SectorHeatmapScene.create(module: module, player: player)
				.navigationTitle(player.name)
				.navigationBarTitleDisplayMode(.inline)
			
		case let .victoryDistribution(player):
			VictoryDistributionScene.create(module: module, player: player)
				.navigationTitle(player.name)
				.navigationBarTitleDisplayMode(.inline)

		case let .shotDistribution(player):
			ShotDistributionScene.create(module: module, player: player)
				.navigationTitle(player.name)
				.navigationBarTitleDisplayMode(.inline)
			
		case let .players(mode):
			PlayerListScene.create(module: module) { player in
				switch mode {
				case .victoryDistribution:
					navigationStack.append(.victoryDistribution(player))
					
				case .shotDistribution:
					navigationStack.append(.shotDistribution(player))
					
				case .heatmapDistribution:
					navigationStack.append(.heatmapDistribution(player))
				}
			}
			.navigationTitle(mode.title)
			.navigationBarTitleDisplayMode(.inline)
			
		case .biggestFinalTurn:
			BiggestFinalTurnScene.create(module: module) { turn in
				navigationStack.append(.dartsBoard(turn))
			}
			.navigationTitle("biggest_final_set_statistics")
			.navigationBarTitleDisplayMode(.inline)
			
		case .bestTurn:
			BestTurnScene.create(module: module) { turn in
				navigationStack.append(.dartsBoard(turn))
			}
				.navigationTitle("best_set_statistics")
				.navigationBarTitleDisplayMode(.inline)
			
		case .averageValues:
			AverageValuesScene.create(module: module)
				.navigationTitle("average_values_statistics")
				.navigationBarTitleDisplayMode(.inline)
			
		case .time:
			TimeScene.create(module: module)
				.navigationTitle("time_statistics")
				.navigationBarTitleDisplayMode(.inline)
			
		case let .dartsBoard(turn):
			DartsBoardScreen(turn: turn)
				.navigationBarTitleDisplayMode(.inline)
				.navigationTitle("view_turn")
		}
	}
}
