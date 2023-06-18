import SwiftUI

internal struct AverageValuesScreen: View {
	@StateObject var viewModel: IOSAverageValuesViewModel
	
	var body: some View {
		content
			.background(Color.background)
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
	
	@ViewBuilder
	private var content: some View {
		if viewModel.state.isLoading {
			LoadingView()
		} else if viewModel.state.isEmpty() {
			NoDataView()
		} else {
			ScrollView(showsIndicators: false) {
				VStack(spacing: 0) {
					header
					
					averageValues(
						title: String(localized: "all_players"),
						avgTurnScore: viewModel.state.averageTurnOfAll,
						avgShotScore: viewModel.state.averageShotOfAll
					)
					
					ForEach(viewModel.state.playersAverageValues) { value in
						averageValues(
							title: value.player.name,
							avgTurnScore: value.setScore,
							avgShotScore: value.shotValue
						)
					}
				}
			}
		}
	}
	
	@ViewBuilder
	private var header: some View {
		TableHeader(columns: [
			String(localized: "player"),
			String(localized: "average_set"),
			String(localized: "average_shot")
		])
	}
	
	@ViewBuilder
	private func averageValues(
		title: String,
		avgTurnScore: Double,
		avgShotScore: Double
	) -> some View {
		TableRow(columns: [
			title,
			String(format: "%.2f", avgTurnScore),
			String(format: "%.2f", avgShotScore)
		])
	}
}
