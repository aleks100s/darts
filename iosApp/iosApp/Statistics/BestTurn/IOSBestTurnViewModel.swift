import shared

internal final class IOSBestTurnViewModel: ObservableObject {
	@Published var state = BestSetState(playersBestSets: [])
	
	private let viewModel: BestSetViewModel
	private let onTurnSelected: (Set) -> Void
	private var handle: DisposableHandle?
	
	init(
		viewModel: BestSetViewModel,
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
