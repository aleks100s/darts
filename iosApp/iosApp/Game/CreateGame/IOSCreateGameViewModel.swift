import Combine
import shared

internal final class IOSCreateGameViewModel: ObservableObject {
	@Published var state = CreateGameState(
		allPlayers: [],
		selectedPlayers: [],
		selectedGoal: nil,
		goalOptions: [101, 301, 501],
		playerToDelete: nil,
		isDeletePlayerDialogShown: false
	)
	
	private let viewModel: CreateGameViewModel
	private let createPlayer: () -> ()
	private let startGame: ([Player], Int32) -> ()
	private var handle: DisposableHandle?
	
	init(
		getPlayersUseCase: GetPlayersUseCase,
		deletePlayerUseCase: DeletePlayerUseCase,
		createPlayer: @escaping () -> (),
		startGame: @escaping ([Player], Int32) -> ()
	) {
		viewModel = CreateGameViewModel(
			deletePlayerUseCase: deletePlayerUseCase,
			getPlayersUseCase: getPlayersUseCase,
			coroutineScope: nil
		)
		self.createPlayer = createPlayer
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
			}
	}
	
	func onEvent(_ event: CreateGameEvent) {
		switch event {
		case .CreatePlayer():
			createPlayer()
			
		default:
			viewModel.onEvent(event: event)
		}
	}
	
	func dispose() {
		handle?.dispose()
	}
	
	deinit {
		handle?.dispose()
	}
}
