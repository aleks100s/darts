import shared

final class IOSPlayerListViewModel: ObservableObject {
	@Published private(set) var state = PlayerListState(players: [], isLoading: true)
	
	private let viewModel: PlayerListViewModel
	private let onPlayerSelected: (Player) -> Void
	private var handle: DisposableHandle?
	
	init(
		viewModel: PlayerListViewModel,
		onPlayerSelected: @escaping (Player) -> Void
	) {
		self.viewModel = viewModel
		self.onPlayerSelected = onPlayerSelected
	}
	
	func select(player: Player) {
		onPlayerSelected(player)
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
