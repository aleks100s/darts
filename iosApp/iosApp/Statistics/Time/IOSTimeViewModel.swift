import shared

final class IOSTimeViewModel: ObservableObject {
	@Published private(set) var state = TimeState(
		totalTimePlayed: nil,
		playersDuration: [],
		isLoading: true
	)
	
	private let viewModel: TimeViewModel
	private var handle: DisposableHandle?
	
	init(viewModel: TimeViewModel) {
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
