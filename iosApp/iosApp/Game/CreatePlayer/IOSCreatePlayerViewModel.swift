import shared

internal final class IOSCreatePlayerViewModel: ObservableObject {
	@Published var state = CreatePlayerState(name: "")
	
	private let viewModel: CreatePlayerViewModel
	private let navigateBack: () -> ()
	private var handle: DisposableHandle?
	
	init(
		createPlayerUseCase: CreatePlayerUseCase,
		navigateBack: @escaping () -> ()
	) {
		viewModel = CreatePlayerViewModel(
			createPlayerUseCase: createPlayerUseCase,
			coroutineScope: nil
		)
		self.navigateBack = navigateBack
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				guard let state else { return }
				self?.state = state
			}
	}
	
	func onEvent(_ event: CreatePlayerEvent) {
		viewModel.onEvent(event: event)
		navigateBack()
	}
	
	func dispose() {
		handle?.dispose()
	}
	
	deinit {
		handle?.dispose()
	}
}
