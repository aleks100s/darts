import SwiftUI

struct PlayerHistoryHeader: View {
	private let cells = [
		String(localized: "shot1"),
		String(localized: "shot2"),
		String(localized: "shot3"),
		String(localized: "shot_sum"),
		String(localized: "reminder")
	]
	
	var body: some View {
		Row(cells: cells, spacing: 0)
			.background(Color.surface)
	}
}
