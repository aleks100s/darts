import SwiftUI
import shared

struct CreateGameScreen: View {
	@StateObject var viewModel: IOSCreateGameViewModel
	
	var body: some View {
		content
		.alert("enter_new_player_name", isPresented: $viewModel.isCreatePlayerDialogShown) {			
			TextField("enter_new_player_name", text: $viewModel.newPlayerName)
			
			Button {
				viewModel.createPlayer()
			} label: {
				Text("create_player")
			}

			Button {
				viewModel.isCreatePlayerDialogShown = false
				viewModel.newPlayerName = ""
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
				viewModel.start()
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
	private var content: some View {
		List {
			allPlayersSection
			gameGoalSection
			additionalSettings
		}
	}
	
	@ViewBuilder
	private var allPlayersSection: some View {
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
	}
	
	@ViewBuilder
	private var gameGoalSection: some View {
		Section("game_goal") {
			ForEach(viewModel.state.goalOptions) { option in
				let isSelected = viewModel.state.isGoalSelected(goal: option.int32Value)
				GoalOption(option: option.intValue, isSelected: isSelected) {
					viewModel.onEvent(.SelectGoal(option: option.int32Value))
				}
			}
		}
	}
	
	@ViewBuilder
	private var additionalSettings: some View {
		Section("additional_settings") {
			Toggle("finish_with_doubles", isOn: $viewModel.isFinishWithDoublesChecked)
			Toggle("turn_limit", isOn: $viewModel.isTurnLimitEnabled)
			Toggle("randomize_player_order", isOn: $viewModel.isRandomPlayerOrderChecked)
			Toggle("enable_statistics", isOn: $viewModel.isStatisticsEnabled)
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
