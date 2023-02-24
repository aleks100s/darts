import shared

internal final class IOSGameViewModel: ObservableObject {
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
	private var handle: DisposableHandle?
	
	init(gameManager: GameManager) {
		viewModel = GameViewModel(
			gameManager: gameManager,
			coroutineScope: nil
		)
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				guard let state else { return }
				self?.state = state
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
