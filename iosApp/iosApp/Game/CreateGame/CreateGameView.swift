import SwiftUI
import shared

internal struct CreateGameView: View {
	@StateObject var viewModel: IOSCreateGameViewModel
	@State private var newPlayerName = ""
	
	var body: some View {
		List {
			Section("all_players") {
				Button {
					viewModel.onEvent(.CreatePlayer())
				} label: {
					Text("add_new_player")
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
				}
			}
			
			Section("game_goal") {
				ForEach(viewModel.state.goalOptions) { option in
					let isSelected = viewModel.state.selectedGoal == option
					GoalOption(option: option.intValue, isSelected: isSelected) {
						viewModel.onEvent(.SelectGoal(option: option.int32Value))
					}
				}
			}
		}
		.alert("enter_new_player_name", isPresented: $viewModel.isCreatePlayerDialogShown) {
			TextField("enter_new_player_name", text: $newPlayerName)
			
			Button {
				viewModel.onEvent(.SavePlayer(name: newPlayerName))
			} label: {
				Text("create_player")
			}

			Button {
				viewModel.isCreatePlayerDialogShown = false
				newPlayerName = ""
			} label: {
				Text("cancel")
			}
		}
		.alert("delete_player", isPresented: $viewModel.isDeletePlayerDialogShown) {
			Button(role: .destructive) {
				viewModel.onEvent(.DeletePlayer())
			} label: {
				Text("delete")
			}

			Button(role: .cancel) {
				viewModel.onEvent(.HideDeletePlayerDialog())
			} label: {
				Text("cancel")
			}
		}
		.toolbar {
			Button {
				viewModel.start()
			} label: {
				Text("start_game")
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
