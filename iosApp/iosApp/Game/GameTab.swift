import SwiftUI
import shared

struct GameTab: View {
	let module: AppModule
	
	@State private var navigationStack: [GameTabNavigation] = []
	@State private var isGameSettingsSheetShown = false
	
	var body: some View {
		NavigationStack(path: $navigationStack) {
			GameListScene.create(
				using: module,
				onNavigation: { navigation in
					switch navigation {
					case .createGame:
						navigationStack.append(.createGame)
						
					case let .gameSelected(game):
						navigationStack.append(.history(game: game))
						
					case let .replay(game):
						navigationStack.append(.game(settings: game.getGameSettings()))
						
					case .showCalculator:
						navigationStack.append(.calculator)
					}
				}
			)
				.navigationTitle("games")
				.navigationDestination(for: GameTabNavigation.self) {
					navigate(to: $0)
				}
		}
		.onOpenURL { url in
			if url.canBeNavigated(to: GameTabNavigation.createGame.urlComponent) {
				if !navigationStack.isGameRunning {
					navigationStack = [.createGame]
				}
			} else if url.canBeNavigated(to: GameTabNavigation.calculator.urlComponent) {
				if !navigationStack.isGameRunning {
					navigationStack = [.calculator]
				}
			}
		}
	}
	
	@MainActor
	@ViewBuilder
	private func navigate(to scene: GameTabNavigation) -> some View {
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
				onNavigation: { navigation in
					switch navigation {
					case .gameFinished:
						navigationStack = []
						
					case let .showInGameHistory(history, goal, page):
						navigationStack.append(
							.inGameHistory(gameHistory: history, goal: goal, page: page)
						)
						
					case let .turnSelected(turn):
						navigationStack.append(.dartsBoard(turn))
						
					case .replayGame:
						navigationStack.append(
							.game(settings: settings)
						)
						
					case .showGameSettings:
						isGameSettingsSheetShown = true
					}
				}
			)
				.navigationBarTitleDisplayMode(.inline)
				.navigationBarBackButtonHidden()
				.sheet(isPresented: $isGameSettingsSheetShown) {
					GameSettingsScreen(gameSettings: settings) {
						isGameSettingsSheetShown = false
					}
					.presentationDetents([.medium])
				}
			
		case let .history(game):
			HistoryScene.create(
				using: module,
				game: game,
				onNavigate: { navigation in
					switch navigation {
					case let .turnSelected(turn):
						navigationStack.append(.dartsBoard(turn))
						
					case let .showRecap(state):
						navigationStack.append(.gameRecap(historyState: state))
					}
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
			RecapScreen(
				history: historyState.gameHistory,
				averageTurns: historyState.averageTurns(),
				biggestTurns: historyState.biggestTurns(),
				smallestTurns: historyState.smallestTurns(),
				misses: historyState.misses(),
				overkills: historyState.overkills(),
				duration: historyState.duration,
				numberOfTurns: historyState.numberOfTurns
			)
			.navigationBarTitleDisplayMode(.inline)
			.navigationTitle("game_recap")
			
		case .calculator:
			CalculatorScene.create(
				onShowHistoryClick: { turns in
					navigationStack.append(.calculatorHistory(turns: turns))
				}
			)
				.navigationBarTitleDisplayMode(.inline)
				.navigationTitle("calculator")
			
		case let .calculatorHistory(turns):
			CalculatorHistoryScreen(turns: turns) { turn in
				guard !turn.shots.isEmpty else { return }
				
				navigationStack.append(.dartsBoard(turn))
			}
				.navigationBarTitleDisplayMode(.inline)
				.navigationTitle("history")
		}
	}
}
