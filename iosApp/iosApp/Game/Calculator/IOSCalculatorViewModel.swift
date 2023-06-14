import shared

final class IOSCalculatorViewModel: ObservableObject {
	@Published private(set) var state = CalculatorState(
		turns: []
	)
	
	private let viewModel: CalculatorViewModel
	private let onPlayerClick: (Int) -> Void
	
	private var handle: DisposableHandle?
	
	init(
		viewModel: CalculatorViewModel,
		onPlayerClick: @escaping (Int) -> Void
	) {
		self.viewModel = viewModel
		self.onPlayerClick = onPlayerClick
	}
	
	func onEvent(event: CalculatorEvent) {
		viewModel.onEvent(event: event)
	}
	
	func playerClick(index: Int) {
		onPlayerClick(index)
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
