import SwiftUI
import shared

internal struct GameTab: View {
	let module: AppModule
	
	@State private var navigationStack: [GameNavigation] = []
	@State private var isCreatePlayerShown: Bool = false
	
	var body: some View {
		NavigationStack(path: $navigationStack) {
			GameListScene.create(using: module) { game in
				navigationStack.append(.history(game: game))
			}
				.navigationTitle("games")
				.toolbar {
					Button {
						navigationStack.append(.createGame)
					} label: {
						Label("create_game", systemImage: "plus")
					}
				}
				.navigationDestination(for: GameNavigation.self) {
					navigate(to: $0)
				}
		}
		.sheet(isPresented: $isCreatePlayerShown) {
			CreatePlayerScene.create(using: module) {
				isCreatePlayerShown = false
			}
			.presentationDetents([.medium])
		}
	}
	
	@MainActor
	@ViewBuilder
	private func navigate(to scene: GameNavigation) -> some View {
		switch scene {
		case .createGame:
			CreateGameScene.create(
				using: module,
				createPlayer: {
					isCreatePlayerShown = true
				},
				startGame: { players, goal in
					navigationStack.append(.game(players: players, goal: goal))
				}
			)
			.navigationTitle("create_game")
			.navigationBarTitleDisplayMode(.inline)
			
		case let .game(players, goal):
			GameScene.create(
				using: module,
				players: players,
				goal: goal,
				onGameFinished: {
					navigationStack = []
				},
				onShowInGameHistory: { history, goal in
					navigationStack.append(.inGameHistory(gameHistory: history, goal: goal))
				}
			)
				.toolbar(.hidden, for: .tabBar)
				.navigationTitle("game")
				.navigationBarTitleDisplayMode(.inline)
				.navigationBarBackButtonHidden()
			
		case let .history(game):
			HistoryScene.create(
				using: module,
				game: game,
				onTurnSelected: { turn in
					navigationStack.append(.darts(turn))
				}
			)
				.navigationBarTitleDisplayMode(.inline)
				.navigationTitle("history")
			
		case let .inGameHistory(gameHistory, goal):
			GameHistoryView(
				gameHistory: gameHistory,
				gameGoal: goal,
				onTurnSelected: { turn in
					navigationStack.append(.darts(turn))
				}
			)
				.navigationBarTitleDisplayMode(.inline)
				.navigationTitle("history")
			
		case let .darts(turn):
			DartsBoardView(turn: turn)
				.navigationBarTitleDisplayMode(.inline)
				.navigationTitle("view_turn")
		}
	}
}
