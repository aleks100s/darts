import SwiftUI

internal struct AverageValuesView: View {
	@StateObject var viewModel: IOSAverageValuesViewModel
	
	var body: some View {
		view
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
	
	@ViewBuilder
	private var view: some View {
		if viewModel.state.isEmpty() {
			NoDataView()
		} else {
			ScrollView(showsIndicators: false) {
				VStack(spacing: 0) {
					header
					
					averageValues(
						title: String(localized: "all_players"),
						avgSetScore: viewModel.state.averageSetOfAll,
						avgShotScore: viewModel.state.averageShotOfAll
					)
					
					ForEach(viewModel.state.playersAverageValues) { value in
						averageValues(
							title: value.player.name,
							avgSetScore: value.setScore,
							avgShotScore: value.shotValue
						)
					}
					.background(Color.background)
				}
			}
		}
	}
	
	@ViewBuilder
	private var header: some View {
		Row(
			cells: [
				String(localized: "player"),
				String(localized: "average_set"),
				String(localized: "average_shot")
			],
			spacing: 0
		)
		.background(Color.surface)
	}
	
	@ViewBuilder
	private func averageValues(
		title: String,
		avgSetScore: Double,
		avgShotScore: Double
	) -> some View {
		Row(
			cells: [
				title,
				String(format: "%.2f", avgSetScore),
				String(format: "%.2f", avgShotScore)
			],
			spacing: 0
		)
	}
}
