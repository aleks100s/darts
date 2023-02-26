import SwiftUI

internal struct PlayerHistoryHeader: View {
	private let cells = [
		"Hit #1",
		"Hit #2",
		"Hit #3",
		"Sum",
		"Reminder"
	]
	
	var body: some View {
		Row(cells: cells, spacing: 0)
	}
}
