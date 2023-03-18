import SwiftUI
import Charts

internal struct VictoryDistributionView: View {
	@StateObject var viewModel: IOSVictoryDistributionViewModel
	
	var body: some View {
		content
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
	
	@ViewBuilder
	private var content: some View {
		if viewModel.totalCount == 0 {
			NoDataView()
		} else {
			ScrollView {
				VStack(spacing: 32) {
					ZStack {
						Pie(slices: $viewModel.data)
						Text("games_count \(viewModel.totalCount)")
					}
					ChartLegend(legend: $viewModel.legend)
				}
			}
		}
	}
}
