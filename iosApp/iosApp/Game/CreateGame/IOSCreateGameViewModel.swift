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
	@Published var createPlayerState = CreatePlayerState(name: "", allPlayers: [])
	
	private let viewModel: CreateGameViewModel
	private let createPlayerViewModel: CreatePlayerViewModel
	private let startGame: ([Player], Int32) -> ()
	private var handle: DisposableHandle?
	private var createPlayerHandle: DisposableHandle?
	
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
		
		createPlayerHandle = createPlayerViewModel.state
			.subscribe { [weak self] state in
				guard let state else { return }
				self?.createPlayerState = state
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
	
	func createPlayer(name: String) {
		let name = name.trimmingCharacters(in: .whitespacesAndNewlines)
		if !createPlayerState.allPlayers.map(\.name).contains(name), name.count > 2 {
			createPlayerViewModel.onEvent(event: .SavePlayer(name: name))
		}
	}
	
	func dispose() {
		handle?.dispose()
		createPlayerHandle?.dispose()
	}
	
	deinit {
		handle?.dispose()
		createPlayerHandle?.dispose()
	}
}
