import shared

internal final class IOSHistoryViewModel: ObservableObject {
	@Published var state = HistoryState(gameHistory: [], isRecapVisible: false, gameGoal: 0)
	
	private let onTurnSelected: (Set) -> Void
	private let onShowRecap: (HistoryState) -> Void
	private let viewModel: HistoryViewModel
	private var handle: DisposableHandle?
	
	init(
		viewModel: HistoryViewModel,
		onTurnSelected: @escaping (Set) -> Void,
		onShowRecap: @escaping (HistoryState) -> Void
	) {
		self.viewModel = viewModel
		self.onTurnSelected = onTurnSelected
		self.onShowRecap = onShowRecap
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				guard let state else { return }
				self?.state = state
			}
	}
	
	func select(turn: Set) {
		onTurnSelected(turn)
	}
	
	func showRecap() {
		onShowRecap(state)
	}
	
	func onEvent(_ event: HistoryEvent) {
		viewModel.onEvent(event: event)
	}
	
	func dispose() {
		handle?.dispose()
	}
	
	deinit {
		handle?.dispose()
	}
}