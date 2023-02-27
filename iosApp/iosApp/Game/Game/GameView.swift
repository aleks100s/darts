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
				Button {
					viewModel.onEvent(.ChangeTurn())
				} label: {
					Text("next_turn")
				}
				
				Button {
					viewModel.onEvent(.ResetCurrentTurn())
				} label: {
					Text("reset_turn")
				}
			}
			.alert("leave_game", isPresented: $viewModel.isCloseGameDialogShown) {
				Button {
					viewModel.finishGame()
				} label: {
					Text("leave")
				}

				Button {
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
		if viewModel.state.isInputVisible {
			GameInputView(
				state: viewModel.state,
				onInputClick: { sector in
					viewModel.onEvent(.MakeShot(sector: sector))
				},
				onPlayerClick: {
					viewModel.onEvent(.HideGameInput())
				}
			)
		} else {
			VStack {
				GameHistoryView()
				
				Button {
					viewModel.onEvent(.ShowGameInput())
				} label: {
					Text("back_to_game")
				}
			}
		}
	}
}
