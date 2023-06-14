import SwiftUI

struct CalculatorScreen: View {
	@StateObject var viewModel: IOSCalculatorViewModel
	
	var body: some View {
		GameInputView(
			isStatisticsEnabled: false,
			currentTurn: viewModel.state.currentTurn(),
			currentResults: [],
			onInputClick: { sector in
				viewModel.onEvent(event: .MakeShot(sector: sector))
			},
			onPlayerClick: viewModel.playerClick
		)
	}
}
