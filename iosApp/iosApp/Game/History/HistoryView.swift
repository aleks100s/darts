import SwiftUI

internal struct HistoryView: View {
	@StateObject var viewModel: IOSHistoryViewModel
	
	private var idiom : UIUserInterfaceIdiom { UIDevice.current.userInterfaceIdiom }
	
	var body: some View {
		content
		.toolbar {
			Button {
				viewModel.showRecap()
			} label: {
				Text("progress")
			}

		}
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
	
	@ViewBuilder
	private var content: some View {
		if idiom == .pad {
			TabletView(
				view1: { history },
				view2: { recap }
			)
		} else {
			history
		}
	}
	
	@ViewBuilder
	private var history: some View {
		GameHistoryView(
			gameHistory: viewModel.state.gameHistory,
			gameGoal: viewModel.state.gameGoal,
			page: 0,
			onTurnSelected: viewModel.select
		)
	}
	
	@ViewBuilder
	private var recap: some View {
		GameRecapView(
			history: viewModel.state.gameHistory,
			averageSets: viewModel.state.averageSets(),
			biggestSets: viewModel.state.biggestSets(),
			smallestSets: viewModel.state.smallestSets(),
			misses: viewModel.state.misses(),
			overkills: viewModel.state.overkills()
		)
	}
}
