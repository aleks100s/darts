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
	private let onEvent: (CreateGameEvent) -> ()
	private var handle: DisposableHandle?
	
	init(
		getPlayersUseCase: GetPlayersUseCase,
		deletePlayerUseCase: DeletePlayerUseCase,
		onEvent: @escaping (CreateGameEvent) -> ()
	) {
		viewModel = CreateGameViewModel(
			deletePlayerUseCase: deletePlayerUseCase,
			getPlayersUseCase: getPlayersUseCase,
			coroutineScope: nil
		)
		self.onEvent = onEvent
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				guard let state else { return }
				self?.state = state
			}
	}
	
	func onEvent(_ event: CreateGameEvent) {
		viewModel.onEvent(event: event)
		onEvent(event)
	}
	
	func dispose() {
		handle?.dispose()
	}
	
	deinit {
		handle?.dispose()
	}
}
