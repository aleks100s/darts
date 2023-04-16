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
						navigationStack.append(.players(.victoryDistribution))
						
					case .ShowShotDistribution():
						navigationStack.append(.players(.shotDistribution))
						
					case .ShowBiggestFinalTurn():
						navigationStack.append(.biggestFinalTurn)
						
					case .ShowBestTurn():
						navigationStack.append(.bestTurn)
						
					case .ShowAverageValues():
						navigationStack.append(.averageValues)
						
					case .ShowSectorHeatmapDistribution():
						navigationStack.append(.players(.heatmapDistribution))
						
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
			
		case let .dartsBoard(turn):
			DartsBoardView(turn: turn)
				.navigationBarTitleDisplayMode(.inline)
				.navigationTitle("view_turn")
		}
	}
}
