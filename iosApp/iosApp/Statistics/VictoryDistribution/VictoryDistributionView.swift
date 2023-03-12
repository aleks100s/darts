import SwiftUI

internal struct VictoryDistributionView: View {
	@StateObject var viewModel: IOSVictoryDistributionViewModel
	
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
