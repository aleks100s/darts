import SwiftUI

internal struct HistoryView: View {
	@StateObject var viewModel: IOSHistoryViewModel
	
	var body: some View {
		GameHistoryView()
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
}
