import SwiftUI

internal struct GameView: View {
	@StateObject var viewModel: IOSGameViewModel
	
	var body: some View {
		return gameView
			.alert("game_finished", isPresented: $viewModel.isGameFinishedDialogShown) {
				Button {
					viewModel.onEvent(.CloseGame())
					viewModel.finishGame()
				} label: {
					Text("OK")
				}
			} message: {
				Text("winner \(viewModel.state.getWinnerName() ?? "")")
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
}
