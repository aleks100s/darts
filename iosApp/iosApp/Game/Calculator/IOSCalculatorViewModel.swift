import shared

final class IOSCalculatorViewModel: ObservableObject {
	private let viewModel: CalculatorViewModel
	
	init(viewModel: CalculatorViewModel) {
		self.viewModel = viewModel
	}
}
