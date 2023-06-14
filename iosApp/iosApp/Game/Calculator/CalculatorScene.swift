import shared
import SwiftUI

enum CalculatorScene {
	static func create(
		onShowHistoryClick: @escaping () -> Void
	) -> some View {
		let calculatorManager = CalculatorManager()
		let viewModel = CalculatorViewModel(
			calculatorManager: calculatorManager,
			coroutineScope: nil
		)
		let iOSViewModel = IOSCalculatorViewModel(
			viewModel: viewModel,
			onShowHistoryClick: onShowHistoryClick
		)
		let view = CalculatorScreen(viewModel: iOSViewModel)
		return view
	}
}
