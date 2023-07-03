import SwiftUI
import shared

struct GameListScreen: View {
	@StateObject var viewModel: IOSGameListViewModel
	
	var body: some View {
		content
			.toolbar(viewModel.state.isLoading ? .hidden : .visible, for: .navigationBar)
			.toolbar {
				ToolbarItem(placement: .navigationBarTrailing) {
					Button {
						viewModel.navigate(.createGame)
					} label: {
						Label("create_game", systemImage: "plus")
					}
					.accessibilityIdentifier("createGame")
				}

				ToolbarItem(placement: .navigationBarLeading) {
					Button {
						viewModel.navigate(.showCalculator)
					} label: {
						Text("calculator")
					}
					.accessibilityIdentifier("calculator")
				}
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
#if DEBUG
			.onShake {
				viewModel.prepopulateDatabase()
			}
#endif
	}
	
	@ViewBuilder
	private var content: some View {
		if viewModel.state.isLoading {
			LoadingView()
		} else if viewModel.state.isEmpty {
			emptyView
		} else {
			List {
				if !viewModel.state.ongoingGames.isEmpty {
					Section("ongoing_games") {
						gamesList(games: viewModel.state.ongoingGames, isOngoing: true)
					}
				}
				if !viewModel.state.finishedGames.isEmpty {
					Section("finished_games") {
						gamesList(games: viewModel.state.finishedGames, isOngoing: false)
					}
				}
			}
		}
	}
	
	@ViewBuilder
	private var emptyView: some View {
		VStack(spacing: 16) {
			Text("no_data_yet")
			
			Button("create_game") {
				viewModel.navigate(.createGame)
			}
		}
	}
	
	@ViewBuilder
	private func gamesList(games: [Game], isOngoing: Bool) -> some View {
		ForEach(games) { game in
			gameItem(game: game)
				.onTapGesture {
					if !isOngoing {
						viewModel.navigate(.gameSelected(game))
					}
				}
				.swipeActions(edge: .leading, allowsFullSwipe: true) {
					if !isOngoing {
						Button {
							viewModel.navigate(.replay(game))
						} label: {
							Text("replay")
						}
						.tint(.green)
					}
				}
				.contextMenu(
					menuItems: {
						if !isOngoing {
							Button {
								viewModel.navigate(.replay(game))
							} label: {
								Label("replay", systemImage: "repeat")
							}
						}
						
						Button(role: .destructive) {
							viewModel.onEvent(.ShowDeleteGameDialog(game: game))
						} label: {
							Label("delete", systemImage: "trash")
						}
					}
				)
		}
		.onDelete { indexTurn in
			if let index = indexTurn.first {
				if isOngoing {
					viewModel.deleteOngoingGame(index: index)
				} else {
					viewModel.deleteFinishedGame(index: index)
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
					let parts = game.getUITitleStringParts()
					ForEach(parts) { part in
						Text(part)
					}
				}
				.font(.caption)
			}
			
			Spacer()
			
			Chevron()
		}
		.lineLimit(1)
		.contentShape(Rectangle())
	}
}
