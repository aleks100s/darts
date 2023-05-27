import SwiftUI
import shared

internal struct GameListScreen: View {
	@StateObject var viewModel: IOSGameListViewModel
	
	var body: some View {
		content
			.toolbar(viewModel.state.isLoading ? .hidden : .visible, for: .navigationBar)
			.toolbar {
				Button {
					viewModel.createGame()
				} label: {
					Label("create_game", systemImage: "plus")
				}
				.accessibilityIdentifier("createGame")
			}
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
						.swipeActions(edge: .leading, allowsFullSwipe: true) {
							Button {
								viewModel.replay(game: game)
							} label: {
								Text("replay")
							}
							.tint(.green)
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
			VStack(alignment: .leading, spacing: 8) {
				Text(game.titleUIString)
					.font(.caption)
				
				Text(game.getPlayersListString())
				
				HStack(spacing: 8) {
					Text(game.getUIGoalString())
					if !game.duration.isEmpty {
						Text(game.getUIDurationString())
					}
					if game.winner != nil {
						Text(game.getUIWinnerString())
					}
					if game.finishWithDoubles {
						Text(game.getUIDoublesString())
					}
					if game.randomPlayerOrder {
						Text(game.getUIRandomOrderString())
					}
				}
				.font(.caption)
			}
			
			Spacer()
			
			Image(systemName: "chevron.right")
		}
		.lineLimit(1)
	}
}
