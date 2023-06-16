import shared

final class IOSCalculatorViewModel: ObservableObject {
	@Published private(set) var state = CalculatorState(turns: [])
	
	private let viewModel: CalculatorViewModel
	private let onShowHistoryClick: ([Turn]) -> Void
	
	private var handle: DisposableHandle?
	
	init(
		viewModel: CalculatorViewModel,
		onShowHistoryClick: @escaping ([Turn]) -> Void
	) {
		self.viewModel = viewModel
		self.onShowHistoryClick = onShowHistoryClick
	}
	
	func onEvent(_ event: CalculatorEvent) {
		viewModel.onEvent(event: event)
	}
	
	func showHistory() {
		guard state.turnNumber > 0 else { return }
		
		onShowHistoryClick(state.turns)
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				guard let state else { return }
				self?.state = state
			}
	}
	
	func dispose() {
		handle?.dispose()
	}
	
	deinit {
		handle?.dispose()
	}
}
