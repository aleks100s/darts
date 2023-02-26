import SwiftUI

internal struct GameView: View {
	@StateObject var viewModel: IOSGameViewModel
	
	var body: some View {
		return gameView
			.alert("Game over", isPresented: $viewModel.isGameFinishedDialogShown) {
				Button {
					viewModel.onEvent(.CloseGame())
					viewModel.finishGame()
				} label: {
					Text("Ok")
				}
			}
			.alert("Turn is over", isPresented: $viewModel.isTurnOverDialogShown) {
				Button {
					viewModel.onEvent(.ChangeTurn())
				} label: {
					Text("Next turn")
				}
				
				Button {
					viewModel.onEvent(.ResetCurrentTurn())
				} label: {
					Text("Reset turn")
				}
			}
			.alert("Leave the game", isPresented: $viewModel.isCloseGameDialogShown) {
				Button {
					viewModel.onEvent(.CloseGame())
				} label: {
					Text("Leave")
				}

				Button {
					viewModel.onEvent(.ReturnToGame())
				} label: {
					Text("Cancel")
				}
			}
			.toolbar {
				ToolbarItem(placement: .navigationBarLeading) {
					Button {
						viewModel.onEvent(.BackButtonPressed())
					} label: {
						Text("Leave")
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
			GameHistoryView {
				viewModel.onEvent(.ShowGameInput())
			}
		}
	}
}
