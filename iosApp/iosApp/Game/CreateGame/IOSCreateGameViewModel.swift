import Combine
import shared

final class IOSCreateGameViewModel: ObservableObject {
	@Published var isCreatePlayerDialogShown = false
	@Published var isDeletePlayerDialogShown = false
	@Published var newPlayerName = ""
	@Published var isFinishWithDoublesChecked = false
	@Published var isRandomPlayerOrderChecked = true
	@Published var isStatisticsEnabled = true
	@Published var isTurnLimitEnabled = true
	
	@Published private(set) var state = CreateGameState(
		allPlayers: [],
		selectedPlayers: [],
		selectedGoal: nil,
		goalOptions: [101, 301, 501],
		playerToDelete: nil,
		isDeletePlayerDialogShown: false,
		isFinishWithDoublesChecked: false,
		isRandomPlayersOrderChecked: true,
		isStatisticsEnabled: true,
		isTurnLimitEnabled: true
	)
		
	private let viewModel: CreateGameViewModel
	private let createPlayerViewModel: CreatePlayerViewModel
	private let startGame: (GameSettings) -> ()
	
	private var handle: DisposableHandle?
	private var createPlayerHandle: DisposableHandle?
	private var createPlayerState = CreatePlayerState(name: "", allPlayers: [])
	
	init(
		viewModel: CreateGameViewModel,
		createPlayerViewModel: CreatePlayerViewModel,
		startGame: @escaping (GameSettings) -> ()
	) {
		self.viewModel = viewModel
		self.createPlayerViewModel = createPlayerViewModel
		self.startGame = startGame
	}
	
	func start() {
		startGame(
			GameSettings(
				selectedPlayers: state.selectedPlayers,
				selectedGameGoal: state.selectedGoal?.int32Value ?? 0,
				isFinishWithDoublesChecked: isFinishWithDoublesChecked,
				isRandomPlayersOrderChecked: isRandomPlayerOrderChecked,
				isStatisticsEnabled: isStatisticsEnabled,
				isTurnLimitEnabled: isTurnLimitEnabled,
				isResumed: false
			)
		)
	}
	
	func showCreatePlayerDialog() {
		isCreatePlayerDialogShown = true
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
				self?.newPlayerName = state.name
			}
	}
	
	func onEvent(_ event: CreateGameEvent) {
		viewModel.onEvent(event: event)
	}
	
	func deletePlayer(index: Int) {
		let player = state.allPlayers[index]
		onEvent(.ShowDeletePlayerDialog(player: player))
	}
	
	func createPlayer() {
		let name = newPlayerName.trimmingCharacters(in: .whitespacesAndNewlines)
		if isNewPlayerNameValid(name) {
			createPlayerViewModel.onEvent(event: .SavePlayer(name: name))
			newPlayerName = ""
		}
	}
	
	func dispose() {
		handle?.dispose()
		createPlayerHandle?.dispose()
	}
	
	private func isNewPlayerNameValid(_ name: String) -> Bool {
		name.count > 2
			&& !createPlayerState.allPlayers
				.map(\.name)
				.contains(name)
	}
	
	deinit {
		handle?.dispose()
		createPlayerHandle?.dispose()
	}
}
