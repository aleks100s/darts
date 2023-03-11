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
			onPlayerClick: {
				viewModel.showGameHistory()
			}
		)
	}
}
