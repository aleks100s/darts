import shared
import Foundation

@MainActor
final class IOSGameListViewModel: ObservableObject {
	@Published var isDeleteGameDialogShown = false
	@Published private(set) var state = GameListState(
		isDeleteGameDialogShown: false,
		selectedGame: nil,
		isLoading: true,
		isActionsDialogShown: false,
		finishedGames: [],
		pausedGames: []
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
	
	func deleteFinishedGame(index: Int) {
		let game = state.finishedGames[index]
		delete(game: game)
	}
	
	func deletePausedGame(index: Int) {
		let game = state.pausedGames[index]
		delete(game: game)
	}
	
	func onEvent(_ event: GameListEvent) {
		viewModel.onEvent(event: event)
	}
	
	func dispose() {
		handle?.dispose()
	}
	
	private func delete(game: Game) {
		onEvent(.ShowDeleteGameDialog(game: game))
	}
	
	deinit {
		handle?.dispose()
	}
}
