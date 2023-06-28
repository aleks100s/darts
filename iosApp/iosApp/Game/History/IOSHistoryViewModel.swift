import shared

final class IOSHistoryViewModel: ObservableObject {
	@Published private(set) var state = HistoryState(
		gameHistory: [],
		gameGoal: 0,
		isLoading: true,
		duration: GameDuration(minutes: 0, seconds: 0)
	)
	
	private let viewModel: HistoryViewModel
	private let onNavigate: (HistoryNavigation) -> Void
	
	private var handle: DisposableHandle?
	
	init(
		viewModel: HistoryViewModel,
		onNavigate: @escaping (HistoryNavigation) -> Void
	) {
		self.viewModel = viewModel
		self.onNavigate = onNavigate
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				guard let state else { return }
				self?.state = state
			}
	}
	
	func navigate(_ navigation: HistoryNavigation) {
		onNavigate(navigation)
	}
	
	func dispose() {
		handle?.dispose()
	}
	
	deinit {
		handle?.dispose()
	}
}
