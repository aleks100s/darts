import SwiftUI
import SwiftUIPager
import shared

struct GameHistoryView: View {
	let gameHistory: [PlayerHistory]
	let gameGoal: Int32
	let onTurnSelected: (Turn) -> Void
	let page: Page
	
	var body: some View {
		Pager(
			page: page,
			data: gameHistory,
			id: \.player.id,
			content: { playerHistory in
				PlayerHistoryView(
					playerHistory: playerHistory,
					gameGoal: gameGoal,
					onTurnSelected: onTurnSelected
				)
			}
		)
	}
	
	init(
		gameHistory: [PlayerHistory],
		gameGoal: Int32,
		page: Int,
		onTurnSelected: @escaping (Turn) -> Void
	) {
		self.gameHistory = gameHistory
		self.gameGoal = gameGoal
		self.page = .withIndex(page)
		self.onTurnSelected = onTurnSelected
	}
}
