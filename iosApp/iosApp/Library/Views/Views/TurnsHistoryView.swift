import SwiftUI
import shared

struct TurnsHistoryView: View {
	let turns: [Turn]
	let onTurnSelected: (Turn) -> Void
	
	var body: some View {
		VStack(spacing: 0) {
			PlayerHistoryHeader()

			ScrollView(showsIndicators: false) {
				VStack(spacing: 0) {
					ForEach(turns) { turn in
						TurnItem(
							turn: turn,
							shotsLeft: Int(turn.shotsLeft()),
							useTurnColors: true
						) {
							onTurnSelected(turn)
						}
					}
						.background(Color.background)
						.listRowSeparator(.hidden)
						.listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0))
					Spacer()
				}
			}
		}
		.background(Color.background)
	}
}
