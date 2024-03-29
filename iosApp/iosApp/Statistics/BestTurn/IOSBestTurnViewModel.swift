import shared

final class IOSBestTurnViewModel: ObservableObject {
	@Published private(set) var state = BestTurnState(playersBestTurns: [], isLoading: true)
	
	private let viewModel: BestTurnViewModel
	private let onTurnSelected: (Turn) -> Void
	private var handle: DisposableHandle?
	
	init(
		viewModel: BestTurnViewModel,
		onTurnSelected: @escaping (Turn) -> Void
	) {
		self.viewModel = viewModel
		self.onTurnSelected = onTurnSelected
	}
	
	func select(turn: Turn) {
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
