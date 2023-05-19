import SwiftUI
import shared

internal struct GameListScreen: View {
	@StateObject var viewModel: IOSGameListViewModel
	
	var body: some View {
		content
		.alert("delete_game", isPresented: $viewModel.isDeleteGameDialogShown) {
			Button(role: .destructive) {
				viewModel.onEvent(.DeleteGame())
			} label: {
				Text("delete")
			}
			
			Button(role: .cancel) {
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
	private var content: some View {
		if viewModel.state.isLoading {
			LoadingView()
		} else if viewModel.state.games.isEmpty {
			NoDataView()
		} else {
			List {
				ForEach(viewModel.state.games) { game in
					gameItem(game: game)
						.contentShape(Rectangle())
						.onTapGesture {
							viewModel.select(game: game)
						}
				}
				.onDelete { indexTurn in
					if let index = indexTurn.first {
						viewModel.deleteGame(index: index)
					}
				}
			}
		}
	}
	
	@ViewBuilder
	private func gameItem(game: Game) -> some View {
		HStack(alignment: .center, spacing: 16) {
			VStack(alignment: .leading) {
				if game.winner == nil {
					Text("training \(game.id?.int32Value ?? 0) \(game.gameGoal)")
				} else {
					Text("game_title \(game.id?.int32Value ?? 0) \(game.gameGoal)")
				}
				Text(game.getDateString())
			}
			
			Spacer()
			
			if let winner = game.winner {
				Text("🏆 \(winner.name) 🏆")
			}
		}
		.lineLimit(1)
	}
}
