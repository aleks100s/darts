import shared
import Foundation

@MainActor
internal final class IOSGameListViewModel: ObservableObject {
	@Published var isDeleteGameDialogShown = false
	@Published var state = GameListState(games: [], isDeleteGameDialogShown: false, gameToDelete: nil)
	
	private let viewModel: GameListViewModel
	private let onGameSelected: (Game) -> Void
	private var handle: DisposableHandle?
	
	init(
		getGamesUseCase: GetGamesUseCase,
		deleteGameUseCase: DeleteGameUseCase,
		onGameSelected: @escaping (Game) -> Void
	) {
		self.onGameSelected = onGameSelected
		self.viewModel = GameListViewModel(
			deleteGameUseCase: deleteGameUseCase,
			getGamesUseCase: getGamesUseCase,
			coroutineScope: nil
		)
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				guard let state else { return }
				self?.state = state
				self?.isDeleteGameDialogShown = state.isDeleteGameDialogShown
			}
	}
	
	func select(game: Game) {
		onGameSelected(game)
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
