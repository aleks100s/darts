import SwiftUI
import shared

internal struct CreateGameScreen: View {
	@StateObject var viewModel: IOSCreateGameViewModel
	@State private var newPlayerName = ""
	@State private var isFinishWithDoublesChecked = false
	
	var body: some View {
		List {
			Section("all_players") {
				Button {
					viewModel.onEvent(.CreatePlayer())
				} label: {
					Text("add_new_player")
				}
				.accessibilityIdentifier("addNewPlayer")

				ForEach(viewModel.state.allPlayers) { player in
					let isChecked = viewModel.state.isPlayerSelected(player: player)
					PlayerCheckbox(player: player, isChecked: isChecked) {
						viewModel.onEvent(.SelectPlayer(player: player))
					}
				}
				.onDelete { indexTurn in
					if let index = indexTurn.first {
						viewModel.deletePlayer(index: index)
					}
				}
			}
			
			Section("game_goal") {
				ForEach(viewModel.state.goalOptions) { option in
					let isSelected = viewModel.state.isGoalSelected(goal: option.int32Value)
					GoalOption(option: option.intValue, isSelected: isSelected) {
						viewModel.onEvent(.SelectGoal(option: option.int32Value))
					}
				}
			}
			
			Section("additional_settings") {
				Toggle("finish_with_doubles", isOn: $isFinishWithDoublesChecked)
			}
		}
		.alert("enter_new_player_name", isPresented: $viewModel.isCreatePlayerDialogShown) {
			TextField("enter_new_player_name", text: $newPlayerName)
			
			Button {
				if newPlayerName.trimmingCharacters(in: .whitespacesAndNewlines).count > 2 {
					viewModel.createPlayer(name: newPlayerName)
					newPlayerName = ""
				}
			} label: {
				Text("create_player")
			}

			Button {
				viewModel.isCreatePlayerDialogShown = false
				newPlayerName = ""
			} label: {
				Text("cancel")
			}
			.accessibilityIdentifier("createPlayerCancel")
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
			.accessibilityIdentifier("deletePlayerCancel")
		}
		.toolbar {
			Button {
				viewModel.start(isFinishWithDoubles: isFinishWithDoublesChecked)
			} label: {
				Text("start_game")
			}
			.disabled(!viewModel.state.isReadyToCreateGame())
			.accessibilityIdentifier("startGame")
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
