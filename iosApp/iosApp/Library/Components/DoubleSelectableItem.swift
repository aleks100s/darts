import SwiftUI

internal struct DoubleSelectableItem: View {
	let leftText: String
	let rightText: String
	let onSelect: () -> Void
	
	var body: some View {
		Button {
			onSelect()
		} label: {
			HStack {
				Text(leftText)
				Spacer()
				Text(rightText)
				Chevron()
			}
			.padding(.vertical, 4)
		}
		.tint(Color.onBackground)
	}
}
