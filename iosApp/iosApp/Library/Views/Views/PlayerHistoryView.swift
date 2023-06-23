import SwiftUI
import shared

struct PlayerHistoryView: View {
	let playerHistory: PlayerHistory
	let gameGoal: Int32
	let onTurnSelected: (Turn) -> Void
	
	var body: some View {
		VStack(spacing: 0) {
			SectionHeader(title: playerHistory.player.name)
			gameGoalView(goal: gameGoal)
			TurnsHistoryView(turns: playerHistory.turns, onTurnSelected: onTurnSelected)
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
