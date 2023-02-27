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
