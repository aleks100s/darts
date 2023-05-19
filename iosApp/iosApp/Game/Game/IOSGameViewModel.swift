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
		gameGoal: 0,
		averageTurns: []
	)
	
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
