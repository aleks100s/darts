import SwiftUI

struct InputHintRowView: View {
	private let cells = [
		String(localized: "single"),
		String(localized: "double_"),
		String(localized: "triplet")
	]
	
	var body: some View {
		Row(cells: cells, spacing: 0)
			.background(Color.surface)
	}
}
