import SwiftUI

internal struct Row: View {
	let cells: [String]
	let spacing: CGFloat
	
	var body: some View {
		HStack(spacing: spacing) {
			ForEach(cells) { cell in
				Cell(text: cell)
			}
			.background(Color.surface)
		}
		.frame(maxWidth: .infinity)
	}
}
