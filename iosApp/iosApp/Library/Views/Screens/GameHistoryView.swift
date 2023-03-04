import SwiftUI
import SwiftUIPager
import shared

internal struct GameHistoryView: View {
	@StateObject var page: Page = .first()
	let gameHistory: [PlayerHistory]
	let gameGoal: Int32
	
	var body: some View {
		Pager(
			page: page,
			data: gameHistory,
			id: \.player.id,
			content: { playerHistory in
				PlayerHistoryView(playerHistory: playerHistory, gameGoal: gameGoal)
			}
		)
	}
}
