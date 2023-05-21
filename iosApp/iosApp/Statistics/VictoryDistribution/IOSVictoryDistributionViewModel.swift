import shared
import SwiftUI

internal final class IOSVictoryDistributionViewModel: ObservableObject {
	@Published var data: [(Float, Color)] = []
	@Published var legend: [(Color, String)] = []
	@Published private(set) var totalCount: Int32 = 0
	@Published private(set) var isLoading: Bool = true
	
	private let viewModel: VictoryDistributionViewModel
	private var handle: DisposableHandle?
	
	init(viewModel: VictoryDistributionViewModel) {
		self.viewModel = viewModel
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				self?.isLoading = state?.isLoading ?? true
				guard let distribution = state?.distribution else { return }
				
				self?.data = [
					(distribution.losePercent(), .gray),
					(distribution.victoryPercent(), .green)
				]
				
				self?.legend = [
					(.gray, String(format: String(localized: "lose_percent"), distribution.losePercent())),
					(.green, String(format: String(localized: "victory_percent"), distribution.victoryPercent()))
				]
				
				self?.totalCount = distribution.gamesCount
			}
	}
	
	func dispose() {
		handle?.dispose()
	}
	
	deinit {
		handle?.dispose()
	}
}
