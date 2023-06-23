import SwiftUI

struct ShotDistributionScreen: View {
	@StateObject var viewModel: IOSShotDistributionViewModel
	
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
		if viewModel.isLoading {
			LoadingView()
		} else if viewModel.totalCount == 0 {
			NoDataView()
		} else {
			ScrollView {
				VStack(spacing: 32) {
					ZStack {
						Pie(slices: $viewModel.data)
						Text("throws_count \(viewModel.totalCount)")
					}
					ChartLegend(legend: $viewModel.legend)
				}
				.padding()
			}
		}
	}
}
