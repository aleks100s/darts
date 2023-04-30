import SwiftUI

internal struct GameScreen: View {
	@StateObject var viewModel: IOSGameViewModel
	
	private var idiom : UIUserInterfaceIdiom { UIDevice.current.userInterfaceIdiom }
	
	var body: some View {
		return gameView
			.alert("game_finished", isPresented: $viewModel.isGameFinishedDialogShown) {
				Button(role: .cancel) {
					viewModel.onEvent(.CloseGame())
					viewModel.finishGame()
				} label: {
					Text("finish_game")
				}
				
				Button {
					viewModel.replayGame()
				} label: {
					Text("replay")
				}
			} message: {
				Text(gameFinishedMessage)
			}
			.alert("turn_is_over", isPresented: $viewModel.isTurnOverDialogShown) {
				Button(role: .cancel) {
					viewModel.onEvent(.ChangeTurn())
				} label: {
					Text("next_turn")
				}
				
				Button(role: .destructive) {
					viewModel.onEvent(.ResetCurrentTurn())
				} label: {
					Text("reset_turn")
				}
			} message: {
				Text("proceed_to_the_next_turn \(viewModel.state.turnResult())")
			}
			.alert("leave_game", isPresented: $viewModel.isCloseGameDialogShown) {
				Button(role: .destructive) {
					viewModel.finishGame()
				} label: {
					Text("leave")
				}

				Button(role: .cancel) {
					viewModel.onEvent(.ReturnToGame())
				} label: {
					Text("cancel")
				}
			} message: {
				Text("your_progress_will_be_lost")
			}
			.toolbar {
				ToolbarItem(placement: .navigationBarLeading) {
					Button {
						viewModel.onEvent(.BackButtonPressed())
					} label: {
						Text("leave")
					}
				}
				ToolbarItem(placement: .navigationBarTrailing) {
					Button {
						viewModel.onEvent(.EraseHit())
					} label: {
						Text("erase_hit")
					}
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
	private var gameView: some View {
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
			state: viewModel.state,
			onInputClick: { sector in
				viewModel.onEvent(.MakeShot(sector: sector))
			},
			onPlayerClick: { index in
				viewModel.showGameHistory(index: index)
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
				viewModel.selectTurn(turn: turn)
			}
		)
	}
	
	private var gameFinishedMessage: String {
		if let winner = viewModel.state.getWinnerName() {
			return String(localized: "winner \(winner)")
		} else {
			return ""
		}
	}
}
