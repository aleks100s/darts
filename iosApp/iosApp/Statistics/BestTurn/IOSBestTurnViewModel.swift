import shared

internal final class IOSBestTurnViewModel: ObservableObject {
	@Published var state = BestTurnState(playersBestTurns: [])
	
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
