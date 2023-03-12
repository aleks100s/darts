import shared

internal final class IOSVictoryDistributionViewModel: ObservableObject {
	@Published var state = VictoryDistributionState(distribution: nil)
	
	private let viewModel: VictoryDistributionViewModel
	private var handle: DisposableHandle?
	
	init(viewModel: VictoryDistributionViewModel) {
		self.viewModel = viewModel
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				guard let state else { return }
				self?.state = state
			}
	}
	
	func dispose() {
		handle?.dispose()
	}
	
	deinit {
		handle?.dispose()
	}
}
