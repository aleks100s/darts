import SwiftUI
import Charts

internal struct VictoryDistributionView: View {
	@StateObject var viewModel: IOSVictoryDistributionViewModel
	
	var body: some View {
		ScrollView {
			VStack(spacing: 32) {
				ZStack {
					Pie(slices: $viewModel.data)
					Text("games_count \(viewModel.totalCount)")
				}
				ChartLegend(legend: $viewModel.legend)
			}
		}
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
}
