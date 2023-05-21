import SwiftUI
import shared

internal final class IOSBiggestFinalTurnViewModel: ObservableObject {
	@Published private(set) var state = BiggestFinalTurnState(playersBiggestFinalTurns: [], isLoading: true)
	
	private let viewModel: BiggestFinalTurnViewModel
	private let onTurnSelected: (Turn) -> Void
	private var handle: DisposableHandle?
	
	init(viewModel: BiggestFinalTurnViewModel, onTurnSelected: @escaping (Turn) -> Void) {
		self.viewModel = viewModel
		self.onTurnSelected = onTurnSelected
	}
	
	func select(turn: Turn) {
		onTurnSelected(turn)
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
