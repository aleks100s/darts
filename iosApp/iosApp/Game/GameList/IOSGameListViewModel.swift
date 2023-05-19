import shared
import Foundation

@MainActor
internal final class IOSGameListViewModel: ObservableObject {
	@Published var isDeleteGameDialogShown = false
	@Published var state = GameListState(
		games: [],
		isDeleteGameDialogShown: false,
		selectedGame: nil,
		isLoading: true,
		isActionsDialogShown: false
	)
	
	private let viewModel: GameListViewModel
	private let onGameSelected: (Game) -> Void
	private let onReplay: (Game) -> Void
	private var handle: DisposableHandle?
	
	init(
		viewModel: GameListViewModel,
		onGameSelected: @escaping (Game) -> Void,
		onReplay: @escaping (Game) -> Void
	) {
		self.onGameSelected = onGameSelected
		self.viewModel = viewModel
		self.onReplay = onReplay
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				guard let state else { return }
				self?.state = state
				self?.isDeleteGameDialogShown = state.isDeleteGameDialogShown
			}
	}
	
	func deleteGame(index: Int) {
		let game = state.games[index]
		onEvent(.ShowDeleteGameDialog(game: game))
	}
	
	func select(game: Game) {
		onGameSelected(game)
	}
	
	func replay(game: Game) {
		onReplay(game)
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
