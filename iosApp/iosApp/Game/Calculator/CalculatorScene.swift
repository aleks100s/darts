import shared
import SwiftUI

enum CalculatorScene {
	static func create(
		onPlayerClick: @escaping (Int) -> Void
	) -> some View {
		let viewModel = CalculatorViewModel(coroutineScope: nil)
		let iOSViewModel = IOSCalculatorViewModel(
			viewModel: viewModel,
			onPlayerClick: onPlayerClick
		)
		let view = CalculatorScreen(viewModel: iOSViewModel)
		return view
	}
}
