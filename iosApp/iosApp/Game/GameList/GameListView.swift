import SwiftUI
import shared

internal struct GameListView: View {
	@StateObject var viewModel: IOSGameListViewModel
	
	var body: some View {
		List {
			ForEach(viewModel.state.games) { game in
				gameItem(game: game)
					.onTapGesture {
						viewModel.select(game: game)
					}
			}
			.onDelete { indexSet in
				let game = viewModel.state.games[indexSet.first!]
				viewModel.onEvent(.ShowDeleteGameDialog(game: game))
			}
		}
		.alert("delete_game", isPresented: $viewModel.isDeleteGameDialogShown) {
			Button {
				viewModel.onEvent(.DeleteGame())
			} label: {
				Text("delete")
			}
			
			Button {
				viewModel.onEvent(.HideDeleteGameDialog())
			} label: {
				Text("cancel")
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
	private func gameItem(game: Game) -> some View {
		HStack(alignment: .center, spacing: 16) {
			VStack(alignment: .leading) {
				Text("game_title \(game.id?.int32Value ?? 0) \(game.gameGoal)")
				Text(game.getDateString())
			}
			
			Spacer()
			
			Text("🏆 \(game.winner?.name ?? "") 🏆")
		}
		.lineLimit(1)
	}
}
