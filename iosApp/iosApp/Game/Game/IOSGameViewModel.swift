import shared

final class IOSGameViewModel: ObservableObject {
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
		gameResult: nil,
		turnNumber: 0
	)
	
	private let viewModel: GameViewModel
	private let onNavigation: (GameNavigation) -> Void
	private var handle: DisposableHandle?
	
	init(
		viewModel: GameViewModel,
		onNavigation: @escaping (GameNavigation) -> Void
	) {
		self.viewModel = viewModel
		self.onNavigation = onNavigation
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
	
	func navigate(_ navigation: GameNavigation) {
		onNavigation(navigation)
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
