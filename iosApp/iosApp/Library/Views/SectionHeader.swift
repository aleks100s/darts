import SwiftUI

internal struct SectionHeader: View {
	let title: String
	
	var body: some View {
		HStack(alignment: .center) {
			Text(title)
				.foregroundColor(Color.onSurface)
		}
		.frame(maxWidth: .infinity)
		.padding(.vertical, 8)
		.background(Color.surface)
	}
}
