import shared

internal final class IOSGameViewModel: ObservableObject {
	@Published var isTurnOverDialogShown = false
	@Published var isCloseGameDialogShown = false
	@Published var isGameFinishedDialogShown = false
	@Published var state = GameState(
		gameHistory: [],
		currentPlayer: nil,
		isGameFinished: false,
		isCloseGameDialogOpened: false,
		turnState: .IsOngoing(),
		isInputVisible: true,
		gameGoal: 0
	)
	
	private let viewModel: GameViewModel
	private let onGameFinished: () -> Void
	private let onShowInGameHistory: ([PlayerHistory], Int32, Int) -> Void
	private var handle: DisposableHandle?
	
	init(
		gameManager: GameManager,
		onGameFinished: @escaping () -> Void,
		onShowInGameHistory: @escaping ([PlayerHistory], Int32, Int) -> Void
	) {
		self.onGameFinished = onGameFinished
		self.onShowInGameHistory = onShowInGameHistory
		viewModel = GameViewModel(
			gameManager: gameManager,
			coroutineScope: nil
		)
	}
	
	func finishGame() {
		onGameFinished()
	}
	
	func showGameHistory(index: Int) {
		onShowInGameHistory(state.gameHistory, state.gameGoal, index)
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				guard let state else { return }
				self?.state = state
				self?.isTurnOverDialogShown = state.isTurnOver()
				self?.isCloseGameDialogShown = state.isCloseGameDialogOpened
				self?.isGameFinishedDialogShown = state.isGameFinished
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
