import SwiftUI

struct GameScreen: View {
	@StateObject var viewModel: IOSGameViewModel
	
	private var idiom : UIUserInterfaceIdiom { UIDevice.current.userInterfaceIdiom }
	
	var body: some View {
		return content
			.navigationTitle("game_title_with_turn_counter \(viewModel.state.turnNumber)")
			.alert(viewModel.state.gameFinishedTitle, isPresented: $viewModel.isGameFinishedDialogShown) {
				Button(role: .cancel) {
					viewModel.navigate(.gameFinished)
				} label: {
					Text(viewModel.state.gameFinishedDismissButtonTitle)
				}
				.accessibilityIdentifier(viewModel.state.gameFinishedDismissButtonTitle)
				
				if viewModel.state.gameResult != .GamePaused()  {
					Button {
						viewModel.navigate(.replayGame)
					} label: {
						Text("replay")
					}
					.accessibilityIdentifier("replay")
				}
			} message: {
				Text(viewModel.state.gameFinishedText)
			}
			.alert("turn_is_over", isPresented: $viewModel.isTurnOverDialogShown) {
				Button(role: .cancel) {
					viewModel.onEvent(.ChangeTurn())
				} label: {
					Text("next_turn")
				}
				.accessibilityIdentifier("nextTurn")
				
				Button(role: .destructive) {
					viewModel.onEvent(.ResetCurrentTurn())
				} label: {
					Text("reset_turn")
				}
				.accessibilityIdentifier("resetTurn")
			} message: {
				Text("proceed_to_the_next_turn \(viewModel.state.turnResult())")
			}
			.alert("leave_game", isPresented: $viewModel.isCloseGameDialogShown) {
				Button {
					viewModel.onEvent(.SaveGameProgress())
				} label: {
					Text("pause_game")
				}
				.accessibilityIdentifier("pause_game")
				
				Button(role: .destructive) {
					viewModel.navigate(.gameFinished)
				} label: {
					Text("leave_without_saving")
				}
				.accessibilityIdentifier("leave_without_saving")

				Button(role: .cancel) {
					viewModel.onEvent(.ReturnToGame())
				} label: {
					Text("cancel")
				}
				.accessibilityIdentifier("cancel")
			}
			.toolbar {
				ToolbarItem(placement: .navigationBarLeading) {
					Button {
						viewModel.onEvent(.BackButtonPressed())
					} label: {
						Text("leave")
					}
					.accessibilityIdentifier("leaveGame")
				}
				ToolbarItem(placement: .navigationBarTrailing) {
					Button {
						viewModel.navigate(.showGameSettings)
					} label: {
						Image(systemName: "info.circle")
					}
					.accessibilityIdentifier("info")
				}
				ToolbarItem(placement: .navigationBarTrailing) {
					Button {
						viewModel.onEvent(.EraseHit())
					} label: {
						Text("erase_hit")
					}
					.accessibilityIdentifier("eraseHit")
				}
			}
			.onAppear {
				viewModel.startObserving()
				UIApplication.shared.isIdleTimerDisabled = true
			}
			.onDisappear {
				viewModel.dispose()
				UIApplication.shared.isIdleTimerDisabled = false
			}
	}
	
	@ViewBuilder
	private var content: some View {
		if idiom == .pad {
			TabletView(
				view1: {
					gameInput
						.frame(minWidth: 0, maxWidth: .infinity)
				},
				view2: {
					gameHistory
						.frame(minWidth: 0, maxWidth: .infinity)
				}
			)
		} else {
			gameInput
		}
	}
	
	@ViewBuilder
	var gameInput: some View {
		GameInputView(
			isStatisticsEnabled: viewModel.state.isStatisticsEnabled,
			currentTurn: viewModel.state.getCurrentTurn(),
			currentResults: viewModel.state.currentResults(),
			onInputClick: { sector in
				viewModel.onEvent(.MakeShot(sector: sector))
			},
			onPlayerClick: { index in
				if idiom == .phone {
					viewModel.navigate(.showInGameHistory(viewModel.state.gameHistory, viewModel.state.gameGoal, index))
				}
			}
		)
	}
	
	@ViewBuilder
	var gameHistory: some View {
		GameHistoryView(
			gameHistory: viewModel.state.gameHistory,
			gameGoal: viewModel.state.gameGoal,
			page: Int(viewModel.state.currentPage()),
			onTurnSelected: { turn in
				viewModel.navigate(.turnSelected(turn))
			}
		)
	}
}
