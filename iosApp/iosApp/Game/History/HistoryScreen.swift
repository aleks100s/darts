import SwiftUI

struct HistoryScreen: View {
	@StateObject var viewModel: IOSHistoryViewModel
	
	private var idiom : UIUserInterfaceIdiom { UIDevice.current.userInterfaceIdiom }
	
	var body: some View {
		content
		.toolbar {
			if idiom == .phone {
				Button {
					viewModel.showRecap()
				} label: {
					Text("progress")
				}
				.accessibilityIdentifier("recap")
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
		if viewModel.state.isLoading {
			LoadingView()
		} else {
			if idiom == .pad {
				TabletView(
					view1: { history },
					view2: { recap }
				)
			} else {
				history
			}
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
			averageTurns: viewModel.state.averageTurns(),
			biggestTurns: viewModel.state.biggestTurns(),
			smallestTurns: viewModel.state.smallestTurns(),
			misses: viewModel.state.misses(),
			overkills: viewModel.state.overkills(),
			duration: viewModel.state.duration,
			numberOfTurns: viewModel.state.numberOfTurns
		)
	}
}
