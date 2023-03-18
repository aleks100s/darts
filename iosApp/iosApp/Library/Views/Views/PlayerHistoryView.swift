import SwiftUI
import shared

internal struct PlayerHistoryView: View {
	let playerHistory: PlayerHistory
	let gameGoal: Int32
	let onTurnSelected: (Set) -> Void
	
	var body: some View {
		VStack(spacing: 0) {
			SectionHeader(title: playerHistory.player.name)
			gameGoalView(goal: gameGoal)
			PlayerHistoryHeader()
			
			ScrollView(showsIndicators: false) {
				ForEach(playerHistory.turns) { turn in
					TurnItem(turn: turn, shotsLeft: Int(turn.shotsLeft())) {
						onTurnSelected(turn)
					}
				}
					.background(Color.background)
					.listRowSeparator(.hidden)
					.listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0))
				Spacer()
			}
		}
		.frame(maxWidth: .infinity, maxHeight: .infinity)
		.background(Color.background)
	}
	
	@ViewBuilder
	private func gameGoalView(goal: Int32) -> some View {
		HStack(alignment: .center) {
			Text("game_goal_value \(gameGoal)")
		}
		.frame(maxWidth: .infinity)
		.padding(.vertical, 8)
		.background(Color.surface)
	}
}
