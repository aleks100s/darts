import shared

internal final class IOSMostFrequentShotsViewModel: ObservableObject {
	@Published var state = MostFrequentShotsState(mostFrequentShots: [])
	
	private let viewModel: MostFrequentShotsViewModel
	private let onTurnSelected: (Set) -> Void
	private var handle: DisposableHandle?
	
	init(
		viewModel: MostFrequentShotsViewModel,
		onTurnSelected: @escaping (Set) -> Void
	) {
		self.viewModel = viewModel
		self.onTurnSelected = onTurnSelected
	}
	
	func select(turn: Set) {
		onTurnSelected(turn)
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
