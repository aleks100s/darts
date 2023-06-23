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
	private let onCreateGame: () -> Void
	private let onGameSelected: (Game) -> Void
	private let onReplay: (Game) -> Void
	private let onShowCalculator: () -> Void
	private var handle: DisposableHandle?
	
	init(
		viewModel: GameListViewModel,
		onCreateGame: @escaping () -> Void,
		onGameSelected: @escaping (Game) -> Void,
		onReplay: @escaping (Game) -> Void,
		onShowCalculator: @escaping () -> Void
	) {
		self.viewModel = viewModel
		self.onCreateGame = onCreateGame
		self.onGameSelected = onGameSelected
		self.onReplay = onReplay
		self.onShowCalculator = onShowCalculator
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				guard let state else { return }
				self?.state = state
				self?.isDeleteGameDialogShown = state.isDeleteGameDialogShown
			}
	}
	
	func createGame() {
		onCreateGame()
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
	
	func prepopulateDatabase() {
		viewModel.prepopulateDatabase()
	}
	
	func showCalculator() {
		onShowCalculator()
	}
	
	deinit {
		handle?.dispose()
	}
}
