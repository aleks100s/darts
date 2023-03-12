import SwiftUI

internal struct ShotDistributionView: View {
	@StateObject var viewModel: IOSShotDistributionViewModel
	
	var body: some View {
		ScrollView {
			VStack(spacing: 32) {
				Pie(slices: $viewModel.data)
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
