import SwiftUI
import shared

internal struct GameTab: View {
	let module: AppModule
	
	@State private var navigationStack: [GameNavigation] = []
	@State private var isCreatePlayerShown: Bool = false
	
	var body: some View {
		NavigationStack(path: $navigationStack) {
			GameListScene.create(
				using: module,
				onCreateGame: {
					navigationStack.append(.createGame)
				},
				onGameSelected: { game in
					navigationStack.append(.history(game: game))
				},
				onReplay: { game in
					navigationStack.append(.game(settings: game.getGameSettings()))
				}
			)
				.navigationTitle("games")
				.navigationDestination(for: GameNavigation.self) {
					navigate(to: $0)
				}
		}
		.onOpenURL { url in
			if url.canBeNavigated(to: GameNavigation.createGame.urlComponent)  {
				if !navigationStack.isGameRunning {
					navigationStack = [.createGame]
				}
			}
		}
	}
	
	@MainActor
	@ViewBuilder
	private func navigate(to scene: GameNavigation) -> some View {
		switch scene {
		case .createGame:
			CreateGameScene.create(
				using: module,
				startGame: { settings in
					navigationStack.append(.game(settings: settings))
				}
			)
			.navigationTitle("create_game")
			.navigationBarTitleDisplayMode(.inline)
			
		case let .game(settings):
			GameScene.create(
				using: module,
				gameSettings: settings,
				onGameFinished: {
					navigationStack = []
				},
				onShowInGameHistory: { history, goal, page in
					navigationStack.append(
						.inGameHistory(gameHistory: history, goal: goal, page: page)
					)
				},
				onTurnSelected: { turn in
					navigationStack.append(.dartsBoard(turn))
				},
				onGameReplaySelected: {
					navigationStack.append(
						.game(settings: settings)
					)
				}
			)
				.navigationTitle(settings.isFinishWithDoublesChecked ? "game_with_doubles" : "game")
				.navigationBarTitleDisplayMode(.inline)
				.navigationBarBackButtonHidden()
			
		case let .history(game):
			HistoryScene.create(
				using: module,
				game: game,
				onTurnSelected: { turn in
					navigationStack.append(.dartsBoard(turn))
				},
				onShowRecap: { state in
					navigationStack.append(.gameRecap(historyState: state))
				}
			)
				.navigationBarTitleDisplayMode(.inline)
				.navigationTitle("history")
			
		case let .inGameHistory(gameHistory, goal, page):
			GameHistoryView(
				gameHistory: gameHistory,
				gameGoal: goal,
				page: page,
				onTurnSelected: { turn in
					navigationStack.append(.dartsBoard(turn))
				}
			)
				.navigationBarTitleDisplayMode(.inline)
				.navigationTitle("history")
			
		case let .dartsBoard(turn):
			DartsBoardScreen(turn: turn)
				.navigationBarTitleDisplayMode(.inline)
				.navigationTitle("view_turn")
			
		case let .gameRecap(historyState):
			GameRecapView(
				history: historyState.gameHistory,
				averageTurns: historyState.averageTurns(),
				biggestTurns: historyState.biggestTurns(),
				smallestTurns: historyState.smallestTurns(),
				misses: historyState.misses(),
				overkills: historyState.overkills(),
				duration: historyState.duration
			)
			.navigationBarTitleDisplayMode(.inline)
			.navigationTitle("game_recap")
		}
	}
}
