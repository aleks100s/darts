import shared

internal final class IOSShotDistributionViewModel: ObservableObject {
	@Published var state = ShotDistributionState(distribution: nil)
	
	private let viewModel: ShotDistributionViewModel
	private var handle: DisposableHandle?
	
	init(viewModel: ShotDistributionViewModel) {
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
