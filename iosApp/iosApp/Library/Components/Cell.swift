import SwiftUI

internal struct Cell: View {
	let text: String
	
	var body: some View {
		HStack(alignment: .center) {
			Text(text)
				.foregroundColor(Color.onSurface)
		}
		.padding(.vertical, 12)
		.padding(.horizontal, 16)
		.frame(maxWidth: .infinity)
	}
}
