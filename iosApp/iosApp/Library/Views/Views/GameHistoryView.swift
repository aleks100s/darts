import SwiftUI
import SwiftUIPager
import shared

internal struct GameHistoryView: View {
	let gameHistory: [PlayerHistory]
	let gameGoal: Int32
	let onTurnSelected: (Set) -> Void
	
	@StateObject var page: Page = .first()
	
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
}
