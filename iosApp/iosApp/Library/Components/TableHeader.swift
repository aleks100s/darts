import SwiftUI

struct TableHeader: View {
	let columns: [String]
	
	var body: some View {
		Row(cells: columns, spacing: 0)
			.background(Color.surface)
	}
}
