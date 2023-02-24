import SwiftUI
import shared

internal struct CreateGameView: View {
	@StateObject var viewModel: IOSCreateGameViewModel
	@State private var isDeletePlayerDialogShown = false
	
	var body: some View {
		List {
			Section("Players") {
				Button {
					viewModel.onEvent(.CreatePlayer())
				} label: {
					Text("Add new player")
				}

				ForEach(viewModel.state.allPlayers) { player in
					let isChecked = viewModel.state.selectedPlayers.contains(player)
					PlayerCheckbox(player: player, isChecked: isChecked) {
						viewModel.onEvent(.SelectPlayer(player: player))
					}
				}
				.onDelete { indexSet in
					let player = viewModel.state.allPlayers[indexSet.first!]
					viewModel.onEvent(.ShowDeletePlayerDialog(player: player))
					isDeletePlayerDialogShown = true
				}
			}
			
			Section("Game goal") {
				ForEach(viewModel.state.goalOptions) { option in
					let isSelected = viewModel.state.selectedGoal == option
					GoalOption(option: option.intValue, isSelected: isSelected) {
						viewModel.onEvent(.SelectGoal(option: option.int32Value))
					}
				}
			}
		}
		.alert("Delete player", isPresented: $isDeletePlayerDialogShown) {
			Button {
				viewModel.onEvent(.DeletePlayer())
			} label: {
				Text("Delete")
			}

			Button {
				viewModel.onEvent(.HideDeletePlayerDialog())
				isDeletePlayerDialogShown = false
			} label: {
				Text("Cancel")
			}

		}
		.toolbar {
			Button {
				viewModel.start()
			} label: {
				Text("Start game")
			}
			.disabled(!viewModel.state.isReadyToCreateGame())
		}
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
	
	@ViewBuilder
	private func PlayerCheckbox(
		player: Player,
		isChecked: Bool,
		onClick: @escaping () -> ()
	) -> some View {
		Button {
			onClick()
		} label: {
			HStack(alignment: .center, spacing: 16) {
				Text(player.name)
				Spacer()
				Image(systemName: isChecked ? "checkmark.square.fill" : "square")
			}
		}
	}
	
	@ViewBuilder
	private func GoalOption(
		option: Int,
		isSelected: Bool,
		onClick: @escaping () -> ()
	) -> some View {
		Button {
			onClick()
		} label: {
			HStack(alignment: .center, spacing: 16) {
				Text("\(option)")
				Spacer()
				Image(systemName: isSelected ? "checkmark.circle.fill" : "circle")
			}
		}
	}
}
