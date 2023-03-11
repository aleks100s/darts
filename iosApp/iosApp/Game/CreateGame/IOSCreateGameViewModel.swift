import Combine
import shared

internal final class IOSCreateGameViewModel: ObservableObject {
	@Published var isCreatePlayerDialogShown = false
	@Published var isDeletePlayerDialogShown = false
	@Published var state = CreateGameState(
		allPlayers: [],
		selectedPlayers: [],
		selectedGoal: nil,
		goalOptions: [101, 301, 501],
		playerToDelete: nil,
		isDeletePlayerDialogShown: false
	)
	
	private let viewModel: CreateGameViewModel
	private let createPlayerViewModel: CreatePlayerViewModel
	private let startGame: ([Player], Int32) -> ()
	private var handle: DisposableHandle?
	
	init(
		viewModel: CreateGameViewModel,
		createPlayerViewModel: CreatePlayerViewModel,
		startGame: @escaping ([Player], Int32) -> ()
	) {
		self.viewModel = viewModel
		self.createPlayerViewModel = createPlayerViewModel
		self.startGame = startGame
	}
	
	func start() {
		startGame(state.selectedPlayers, state.selectedGoal?.int32Value ?? 0)
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				guard let state else { return }
				self?.state = state
				self?.isDeletePlayerDialogShown = state.isDeletePlayerDialogShown
			}
	}
	
	func onEvent(_ event: CreateGameEvent) {
		switch event {
		case .CreatePlayer():
			isCreatePlayerDialogShown = true
			
		default:
			viewModel.onEvent(event: event)
		}
	}
	
	func onEvent(_ event: CreatePlayerEvent) {
		createPlayerViewModel.onEvent(event: event)
	}
	
	func dispose() {
		handle?.dispose()
	}
	
	deinit {
		handle?.dispose()
	}
}
