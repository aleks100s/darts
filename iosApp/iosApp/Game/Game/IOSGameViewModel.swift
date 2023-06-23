import shared

internal final class IOSGameViewModel: ObservableObject {
	@Published var isTurnOverDialogShown = false
	@Published var isCloseGameDialogShown = false
	@Published var isGameFinishedDialogShown = false
	@Published private(set) var state = GameState(
		gameHistory: [],
		currentPlayer: nil,
		isCloseGameDialogOpened: false,
		turnState: .IsOngoing(),
		gameGoal: 0,
		averageTurns: [],
		isStatisticsEnabled: true,
		gameResult: nil
	)
	
	var gameFinishedTitle: String {
		guard let result = state.gameResult else { return "" }
		
		switch result {
		case .TrainingFinished():
			return String(localized: "training_finished")
			
		default:
			return String(localized: "game_finished")
		}
	}
	
	var gameFinishedText: String {
		guard let result = state.gameResult else { return "" }
		
		switch result {
		case .TrainingFinished():
			return ""
			
		case .Draw():
			return String(localized: "game_result_is_draw")
			
		default:
			guard let name = result.winnerName else { return "" }
			
			return String(localized: "winner \(name)")
		}
	}
	
	private let viewModel: GameViewModel
	private let onGameFinished: () -> Void
	private let onShowInGameHistory: ([PlayerHistory], Int32, Int) -> Void
	private let onTurnSelected: (Turn) -> Void
	private let onGameReplaySelected: () -> Void
	private var handle: DisposableHandle?
	
	init(
		viewModel: GameViewModel,
		onGameFinished: @escaping () -> Void,
		onShowInGameHistory: @escaping ([PlayerHistory], Int32, Int) -> Void,
		onTurnSelected: @escaping (Turn) -> Void,
		onGameReplaySelected: @escaping () -> Void
	) {
		self.onGameFinished = onGameFinished
		self.onShowInGameHistory = onShowInGameHistory
		self.onTurnSelected = onTurnSelected
		self.onGameReplaySelected = onGameReplaySelected
		self.viewModel = viewModel
	}
	
	func finishGame() {
		onGameFinished()
	}
	
	func replayGame() {
		onGameReplaySelected()
	}
	
	func showGameHistory(index: Int) {
		onShowInGameHistory(state.gameHistory, state.gameGoal, index)
	}
	
	func selectTurn(turn: Turn) {
		onTurnSelected(turn)
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				guard let state else { return }
				self?.state = state
				self?.isTurnOverDialogShown = state.isTurnOver()
				self?.isCloseGameDialogShown = state.isCloseGameDialogOpened
				self?.isGameFinishedDialogShown = state.gameResult != nil
			}
	}
	
	func onEvent(_ event: GameEvent) {
		viewModel.onEvent(event: event)
	}
	
	func dispose() {
		handle?.dispose()
	}
	
	deinit {
		handle?.dispose()
	}
}
