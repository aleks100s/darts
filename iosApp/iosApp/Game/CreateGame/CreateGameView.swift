import SwiftUI
import shared

internal struct CreateGameView: View {
	@StateObject var viewModel: IOSCreateGameViewModel
	
	var body: some View {
		List {
			Section("Players") {
				Button {
					viewModel.onEvent(.CreatePlayer())
				} label: {
					Text("Add new player")
				}

				ForEach(viewModel.state.allPlayers) { player in
					PlayerCheckbox(player: player)
				}
			}
			
			Section("Game goal") {
				ForEach(viewModel.state.goalOptions) { option in
					GoalOption(option: option.intValue)
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
	private func PlayerCheckbox(player: Player) -> some View {
		HStack(alignment: .center, spacing: 16) {
			Text(player.name)
		}
	}
	
	@ViewBuilder
	private func GoalOption(option: Int) -> some View {
		Text("\(option)")
	}
}
