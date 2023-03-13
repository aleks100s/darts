import SwiftUI

internal struct ShotDistributionView: View {
	@StateObject var viewModel: IOSShotDistributionViewModel
	
	var body: some View {
		ScrollView {
			VStack(spacing: 32) {
				ZStack {
					Pie(slices: $viewModel.data)
					Text("throws_count \(viewModel.totalCount)")
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
