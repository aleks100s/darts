import shared
import SwiftUI

internal final class IOSShotDistributionViewModel: ObservableObject {
	@Published var data: [(Float, Color)] = []
	@Published var legend: [(Color, String)] = []
	
	private let viewModel: ShotDistributionViewModel
	private var handle: DisposableHandle?
	
	init(viewModel: ShotDistributionViewModel) {
		self.viewModel = viewModel
	}
	
	func startObserving() {
		handle = viewModel.state
			.subscribe { [weak self] state in
				guard let distribution = state?.distribution?.distribution else { return }
				
				self?.data = [
					(distribution.missesPercent(), .gray),
					(distribution.singlesPercent(), .pink),
					(distribution.doublesPercent(), .blue),
					(distribution.triplesPercent(), .yellow),
					(distribution.bullseyePercent(), .red),
					(distribution.doubleBullseyePercent(), .green)
				]
				
				self?.legend = [
					distribution.missesPercent() > 0 ? (.gray, String(format: String(localized: "misses_percent"), distribution.missesPercent())) : nil,
					distribution.singlesPercent() > 0 ? (.pink, String(format: String(localized: "singles_percent"), distribution.singlesPercent())) : nil,
					distribution.doublesPercent() > 0 ?(.blue, String(format: String(localized: "doubles_percent"), distribution.doublesPercent())) : nil,
					distribution.triplesPercent() > 0 ? (.yellow, String(format: String(localized: "triplets_percent"), distribution.triplesPercent())) : nil,
					distribution.bullseyePercent() > 0 ? (.red, String(format: String(localized: "bullseye_percent"), distribution.bullseyePercent())) : nil,
					distribution.doubleBullseyePercent() > 0 ? (.green, String(format: String(localized: "double_bullseye_percent"), distribution.doubleBullseyePercent())) : nil
				]
					.compactMap { $0 }
			}
	}
	
	func dispose() {
		handle?.dispose()
	}
	
	deinit {
		handle?.dispose()
	}
}
