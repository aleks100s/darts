import Combine
import shared

internal final class IOSCreateGameViewModel: ObservableObject {
	@Published var isCreatePlayerDialogShown = false
	@Published var isDeletePlayerDialogShown = false
	@Published private(set) var state = CreateGameState(
		allPlayers: [],
		selectedPlayers: [],
		selectedGoal: nil,
		goalOptions: [101, 301, 501],
		playerToDelete: nil,
		isDeletePlayerDialogShown: false,
		isFinishWithDoublesChecked: false,
		isRandomPlayersOrderChecked: false,
		disableStatistics: false
	)
	@Published private(set) var createPlayerState = CreatePlayerState(name: "", allPlayers: [])
	
	private let viewModel: CreateGameViewModel
	private let createPlayerViewModel: CreatePlayerViewModel
	private let startGame: (GameSettings) -> ()
	private var handle: DisposableHandle?
	private var createPlayerHandle: DisposableHandle?
	
	init(
		viewModel: CreateGameViewModel,
		createPlayerViewModel: CreatePlayerViewModel,
		startGame: @escaping (GameSettings) -> ()
	) {
		self.viewModel = viewModel
		self.createPlayerViewModel = createPlayerViewModel
		self.startGame = startGame
	}
	
	func start(
		isFinishWithDoubles: Bool,
		isRandomPlayerOrder: Bool,
		isStatisticsDisabled: Bool
	) {
		startGame(
			GameSettings(
				selectedPlayers: state.selectedPlayers,
				selectedGameGoal: state.selectedGoal?.int32Value ?? 0,
				isFinishWithDoublesChecked: isFinishWithDoubles,
				isRandomPlayersOrderChecked: isRandomPlayerOrder,
				disableStatistics: isStatisticsDisabled
			)
		)
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
	
	func deletePlayer(index: Int) {
		let player = state.allPlayers[index]
		onEvent(.ShowDeletePlayerDialog(player: player))
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
