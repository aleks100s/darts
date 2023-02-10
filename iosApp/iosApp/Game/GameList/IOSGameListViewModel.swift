import shared
import Foundation

@MainActor
internal final class IOSGameListViewModel: ObservableObject {
	@Published var state = GameListState(games: [])
	
	private let viewModel: GameListViewModel
	
	private var handle: DisposableHandle?
	
	init(getGamesUseCase: GetGamesUseCase) {
		self.viewModel = GameListViewModel(getGamesUseCase: getGamesUseCase, coroutineScope: nil)
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				guard let state else { return }
				self?.state = state
			}
	}
	
	func onEvent(_ event: GameListEvent) {
		viewModel.onEvent(event: event)
	}
	
	func dispose() {
		handle?.dispose()
	}
	
	deinit {
		handle?.dispose()
	}
}
