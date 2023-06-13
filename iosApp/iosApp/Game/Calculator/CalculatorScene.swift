import shared
import SwiftUI

enum CalculatorScene {
	static func create() -> some View {
		let viewModel = CalculatorViewModel(coroutineScope: nil)
		let iOSViewModel = IOSCalculatorViewModel(viewModel: viewModel)
		let view = CalculatorScreen(viewModel: iOSViewModel)
		return view
	}
}
