import SwiftUI

struct CalculatorScreen: View {
	@StateObject var viewModel: IOSCalculatorViewModel
	
	var body: some View {
		content
			.toolbar {
				ToolbarItem(placement: .navigationBarTrailing) {
					Button {
						viewModel.onEvent(.UndoLastShot())
					} label: {
						Text("erase_hit")
					}
					.accessibilityIdentifier("eraseHit")
				}
			}
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
	
	@ViewBuilder
	private var content: some View {
		VStack(spacing: 0) {
			VStack(alignment: .center) {
				Text("game_player_result \(viewModel.state.score)")
					.font(.title)
				
				if let average = viewModel.state.average {
					Text("average_calculator \(average.int32Value)")
						.font(.caption)
				}
				
				Text("turn_number \(viewModel.state.turnNumber)")
					.font(.caption)
			}
			.padding()
			.onTapGesture {
				viewModel.showHistory()
			}

			let turn = viewModel.state.lastTurn
			TurnItem(
				turn: turn,
				shotsLeft: Int(turn.shotsLeft()),
				useTurnColors: false,
				onSelect: {}
			)
				.background(Color.background)
			
			InputHintRowView()
			InputMatrixView { sector in
				viewModel.onEvent(.MakeShot(sector: sector))
			}
		}
	}
}
