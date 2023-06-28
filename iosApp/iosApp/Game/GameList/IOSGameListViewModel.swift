import shared
import Foundation

@MainActor
final class IOSGameListViewModel: ObservableObject {
	@Published var isDeleteGameDialogShown = false
	@Published private(set) var state = GameListState(
		games: [],
		isDeleteGameDialogShown: false,
		selectedGame: nil,
		isLoading: true,
		isActionsDialogShown: false
	)
	
	private let viewModel: GameListViewModel
	private let onNavigation: (GameListNavigation) -> Void
	private var handle: DisposableHandle?
	
	init(
		viewModel: GameListViewModel,
		onNavigation: @escaping (GameListNavigation) -> Void
	) {
		self.viewModel = viewModel
		self.onNavigation = onNavigation
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				guard let state else { return }
				self?.state = state
				self?.isDeleteGameDialogShown = state.isDeleteGameDialogShown
			}
	}
	
	func navigate(_ navigation: GameListNavigation) {
		onNavigation(navigation)
	}
	
	func prepopulateDatabase() {
		viewModel.prepopulateDatabase()
	}
	
	func deleteGame(index: Int) {
		let game = state.games[index]
		onEvent(.ShowDeleteGameDialog(game: game))
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
