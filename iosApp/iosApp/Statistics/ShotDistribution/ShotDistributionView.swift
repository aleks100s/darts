import SwiftUI

internal struct ShotDistributionView: View {
	@StateObject var viewModel: IOSShotDistributionViewModel
	
	var body: some View {
		Text("")
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
}
