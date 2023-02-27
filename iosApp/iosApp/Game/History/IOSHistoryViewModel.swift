import shared

internal final class IOSHistoryViewModel: ObservableObject {
	@Published var state = HistoryState(gameHistory: [], isRecapVisible: false, gameGoal: 0)
	
	private let viewModel: HistoryViewModel
	private var handle: DisposableHandle?
	
	init(viewModel: HistoryViewModel) {
		self.viewModel = viewModel
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				guard let state else { return }
				self?.state = state
			}
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
