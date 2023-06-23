import shared

final class IOSAverageValuesViewModel: ObservableObject {
	@Published private(set) var state = AverageValuesState(
		averageTurnOfAll: 0,
		averageShotOfAll: 0,
		playersAverageValues: [],
		isLoading: true
	)
	
	private let viewModel: AverageValuesViewModel
	private var handle: DisposableHandle?
	
	init(viewModel: AverageValuesViewModel) {
		self.viewModel = viewModel
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
