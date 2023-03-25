import shared

internal final class IOSSectorHeatmapViewModel: ObservableObject {
	@Published var state = SectorHeatmapState(distribution: nil)
	
	private let viewModel: SectorHeatmapViewModel
	private var handle: DisposableHandle?
	
	init(viewModel: SectorHeatmapViewModel) {
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
