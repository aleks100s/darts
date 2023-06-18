import SwiftUI

struct TableRow: View {
	let columns: [String]
	
	var body: some View {
		Row(cells: columns, spacing: 0)
			.background(Color.background)
	}
}
