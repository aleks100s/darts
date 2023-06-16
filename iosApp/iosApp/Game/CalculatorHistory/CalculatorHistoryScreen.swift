import SwiftUI
import shared

struct CalculatorHistoryScreen: View {
	let turns: [Turn]
	let onTurnSelected: (Turn) -> Void
	
	var body: some View {
		TurnsHistoryView(turns: turns, onTurnSelected: onTurnSelected)
	}
}
